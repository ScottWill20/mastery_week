package learn.reserving.domain;

import learn.reserving.data.GuestRepository;
import learn.reserving.data.HostRepository;
import learn.reserving.data.ReservationRepository;
import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;
import org.springframework.stereotype.Service;

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

    public List<Reservation> findReservationsByHost(Host host) {
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getHostId(), i-> i));
        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getGuestId(), i -> i));

        List<Reservation> result = reservationRepository.findReservationsByHost(host);
        for (Reservation reservation : result) {
            reservation.setHost(hostMap.get(reservation.getHost().getHostId()));
            reservation.setGuest(guestMap.get(reservation.getGuest().getGuestId()));
        }
        return result;
    }
}
