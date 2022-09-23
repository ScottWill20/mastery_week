package learn.reserving.data;

import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    final LocalDate start = LocalDate.of(2022,9,16);
    final LocalDate end = LocalDate.of(2022,9,21);

    public final static Host HOST = makeHost();
    public final ArrayList<Host> hosts = new ArrayList<>();

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setResId(1);
        reservation.setCheckIn(start);
        reservation.setCheckOut(end);
        reservation.setGuest(new Guest(1,"Scott","Williams","swilliams@dev-10.com","913 907-5762", "KS"));
        reservation.setHost(makeHost());
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

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return reservation.getResId() == 1;
    }

    @Override
    public boolean deleteByResId(Reservation reservation) throws DataException {
        return reservation.getResId() == 1;
    }
    public static Host makeHost() {
        Host host = new Host();
        host.setHostId("0e4707f4-407e-4ec9-9665-baca0aabe88c");
        host.setLastName("Sisse");
        host.setEmail("jsisse@gmail.com");
        host.setPhone("(555) 5555555");
        host.setAddress("1122 Boogie Woogie Ave");
        host.setState("CA");
        host.setZipCode(12345);
        host.setStandardRate(BigDecimal.valueOf(50));
        host.setWeekendRate(BigDecimal.valueOf(100));
        return host;
    }
}
