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
    public List<Reservation> findResById(String email, int id) {
        Host host = hostRepository.findByEmail(email);
        Guest guest = guestRepository.findById(id);
        List<Reservation> reservations = findGuestReservationAtHost(host,guest);
        return reservations;

    }

    public List<Reservation> findReservationsByHost(String hostEmail) {
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getHostId(), i-> i));
        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getGuestId(), i -> i));

        Host host = hostRepository.findByEmail(hostEmail);

        if (host == null || host.getHostId().isBlank() || host.getHostId() == null) {
            return null;
        }

        List<Reservation> result = reservationRepository.findReservationsByHost(host);

        for (Reservation reservation : result) {
            reservation.setHost(hostMap.get(reservation.getHost().getHostId()));
            reservation.setGuest(guestMap.get(reservation.getGuest().getGuestId()));
        }

        return result;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);

        if (!result.isSuccess()) {
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
        if (reservation.getCheckOut().isBefore(LocalDate.now())) {
            result.addErrorMessage("You cannot delete an old reservation.");
        }
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
        nonOverlappingDates(reservation);
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

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) {
        if (reservation.getHost().getHostId() == null
        || hostRepository.findByEmail(reservation.getHost().getEmail()) == null) {
            result.addErrorMessage("Host does not exist.");
        }
        if (guestRepository.findById(reservation.getGuest().getGuestId()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }
    }

    private void nonOverlappingDates(Reservation reservation) {
        List<Reservation> reservations = reservationRepository.findReservationsByHost(reservation.getHost());

        // TODO pass result

        for (Reservation existingRes : reservations) {
            if (reservation.getResId() == existingRes.getResId()) {
                continue; // skips comparison against itself - false positive
            }
            // encompasses
            if (reservation.getCheckIn().isBefore(existingRes.getCheckIn()) && reservation.getCheckOut().isAfter(existingRes.getCheckIn())) {
                return;
            }
            // check in overlaps
            if (reservation.getCheckIn().isBefore(existingRes.getCheckIn()) && reservation.getCheckOut().isBefore(existingRes.getCheckOut())) {
                return;
            }
            // check out overlaps
            if (reservation.getCheckIn().isBefore(existingRes.getCheckOut()) && reservation.getCheckOut().isAfter(existingRes.getCheckOut())) {
                return;
            }
            // within
            if (reservation.getCheckIn().isAfter(existingRes.getCheckIn()) && reservation.getCheckOut().isBefore(existingRes.getCheckOut())) {
                return;
            }
            // matching dates
            if (reservation.getCheckIn().isEqual(existingRes.getCheckIn()) && reservation.getCheckOut().isEqual(existingRes.getCheckOut())) {
                return;
            }
        }
        reservations.add(reservation);
    }

    // inputs = host & guest
    // filter reservations for host
    // only return for that specific guest

    private List<Reservation> findGuestReservationAtHost(Host host, Guest guest) {
        List<Reservation> result = findReservationsByHost(host.getEmail());
        result = result.stream().filter(g -> g.getGuest().getGuestId() == guest.getGuestId())
                .collect(Collectors.toList());
        return result;
    }




}
