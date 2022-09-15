package learn.reserving.data;

import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    final LocalDate start = LocalDate.of(2022,9,16);
    final LocalDate end = LocalDate.of(2022,9,22);

    Host host = new Host();
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setResId(1);
        reservation.setCheckIn(start);
        reservation.setCheckOut(end);
        reservation.setGuest(new Guest(1,"Scott","Williams","swilliams@dev-10.com","913 907-5762", "KS"));
        reservation.setTotal();
        reservations.add(reservation);

    }
    @Override
    public List<Reservation> findReservationsByHost(Host host) {

        return reservations.stream()
                .filter(i -> i.getHost().getHostId().equals(host.getHostId()))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservation.setResId(13);
        reservations.add(reservation);
        return reservation;
    }
}
