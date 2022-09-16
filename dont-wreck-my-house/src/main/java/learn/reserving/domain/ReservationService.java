package learn.reserving.domain;

import learn.reserving.data.DataException;
import learn.reserving.data.GuestRepository;
import learn.reserving.data.HostRepository;
import learn.reserving.data.ReservationRepository;
import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;

    public ReservationService(ReservationRepository reservationRepository, HostRepository hostRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.hostRepository = hostRepository;
        this.guestRepository = guestRepository;
    }

    // findResById()
    // inputs = email and id
    // call findReservationByHost
    // filter for ID that user inputs
    // return reservation at ID#

    public List<Reservation> findReservationsByHost(String hostEmail) {
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getHostId(), i-> i));
        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getGuestId(), i -> i));

        Host host = hostRepository.findByEmail(hostEmail);

        List<Reservation> result = reservationRepository.findReservationsByHost(host);
        for (Reservation reservation : result) {
            reservation.setHost(hostMap.get(reservation.getHost().getHostId()));
            reservation.setGuest(guestMap.get(reservation.getGuest().getGuestId()));
        }
        return result;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (result.isSuccess()) {
            return result;
        }

        result.setPayload(reservationRepository.add(reservation));
        return result;
    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);

        if (!result.isSuccess()) {
            return result;
        }
        boolean updated = reservationRepository.update(reservation);
        if (!updated) {
            result.addErrorMessage(String.format("Reservation for %s %s from %s to %s does not exist.",
                    reservation.getGuest().getFirstName(),
                    reservation.getGuest().getLastName(),
                    reservation.getCheckIn(),
                    reservation.getCheckOut()));
        }
        return result;
    }

    public Result<Reservation> delete(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();
        if (!reservationRepository.deleteByResId(reservation)) {
            result.addErrorMessage(String.format("Reservation with ID %s does not exist.", reservation.getResId()));
        }
        return result;
    }

    private Result<Reservation> validate(Reservation reservation) {
        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        validateFields(reservation,result);
        if (!result.isSuccess()) {
            return result;
        }

        validateChildrenExist(reservation, result);

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation){
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }

        if (reservation.getCheckIn() == null) {
            result.addErrorMessage("Check-in date is required.");
        }

        if (reservation.getCheckOut() == null) {
            result.addErrorMessage("Check-out date is required.");
        }

        if (reservation.getHost() == null) {
            result.addErrorMessage("Host is required.");
        }

        if (reservation.getGuest() == null) {
            result.addErrorMessage("Guest is required.");
        }
        return result;
    }

    private void validateFields(Reservation reservation, Result<Reservation> result) {
        if (reservation.getCheckIn().isBefore(LocalDate.now())) {
            result.addErrorMessage("Check-in date cannot be in the past.");
        }
        if (reservation.getCheckOut().isBefore(LocalDate.now())) {
            result.addErrorMessage("Check-out date cannot be in the past.");
        }
        if (reservation.getCheckIn().isAfter(reservation.getCheckOut())) {
            result.addErrorMessage("Check-out date cannot be before check-in date.");
        }

    }

//    private void validateNoOverlappingDates(Reservation reservation, Result<Reservation> result) {
//
//        Host host = reservation.getHost();
//        for (Reservation existingRes : )
//    }

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) {
        if (reservation.getHost().getHostId() == null
        || hostRepository.findByEmail(reservation.getHost().getEmail()) == null) {
            result.addErrorMessage("Host does not exist.");
        }
        if (guestRepository.findById(reservation.getGuest().getGuestId()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }
    }

    // inputs = host & guest
    // filter reservations for host
    // only return for that specific guest

}
