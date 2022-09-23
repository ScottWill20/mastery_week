package learn.reserving.domain;

import learn.reserving.data.*;
import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReservationServiceTest {

    final Guest guest = new Guest(1,"Scott","Williams","swilliams@dev-10.com","913 907-5762", "KS");
    final Host host = new Host("0e4707f4-407e-4ec9-9665-baca0aabe88c", "Sisse","jsisse@gmail.com","(555) 5555555","1122 Boogie Woogie Ave","asdfasdf", "CA", 12345, new BigDecimal(50),new BigDecimal(100));

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new HostRepositoryDouble(),
            new GuestRepositoryDouble());


    @Test
    void shouldFindByHostEmail() {
        List<Reservation> actual = service.findReservationsByHost("jsisse@gmail.com");
        assertNotNull(actual);
        assertEquals(2,actual.size());
    }
    @Test
    void shouldNotFindByNullHostEmail() {
        List<Reservation> actual = service.findReservationsByHost(null);
        assertNull(actual);



    }
    @Test
    void shouldNotFindByBlankHostEmail() {
        List<Reservation> actual = service.findReservationsByHost("     ");
        assertNull(actual);
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setCheckIn(LocalDate.of(2024,1,1));
        reservation.setCheckOut(LocalDate.of(2024,1,10));
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal();

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }

    @Test
    void shouldNotAddWithStartDateBeforePresentDay() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setCheckIn(LocalDate.of(2022,9,21));
        reservation.setCheckOut(LocalDate.of(2022,9,25));
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal();

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
    }
    @Test
    void shouldValidateDuplicateDates() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setCheckIn(LocalDate.of(2022,9,16));
        reservation.setCheckOut(LocalDate.of(2022,9,22));
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal();

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }
    @Test
    void shouldCalculateTotal() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setCheckIn(LocalDate.of(2024,1,1));
        reservation.setCheckOut(LocalDate.of(2024,1,10));
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal();

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(new BigDecimal(550),result.getPayload().getTotal());
    }
    @Test
    void shouldUpdateExisting() throws DataException {
        List<Reservation> reservations = service.findResById("jsisse@gmail.com",1);
        int reservationId = 1;
        Reservation reservation = reservations.stream().filter(i -> i.getResId() == reservationId)
                .findFirst().orElse(null);

        assert reservation != null;
        reservation.setCheckIn(LocalDate.of(2024,1,11));
        reservation.setCheckOut(LocalDate.of(2024,1,20));

        Result<Reservation> result = service.update(reservation);
//        assertTrue(result.isSuccess());
        assertEquals(0, result.getErrorMessages().size());
    }
    @Test
    void shouldNotUpdateNonExisting() throws DataException {
        List<Reservation> reservations = service.findResById("jsisse@gmail.com",2);
        int reservationId = 3;
        Reservation reservation = reservations.stream().filter(i -> i.getResId() == reservationId)
                .findFirst().orElse(null);

        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
    }

    @Test
    void shouldDelete() throws DataException {
        List<Reservation> reservations = service.findResById("jsisse@gmail.com",2);
        int reservationId = 2;
        Reservation reservation = reservations.stream().filter(i -> i.getResId() == reservationId)
                .findFirst().orElse(null);

        assert reservation != null;
        Result<Reservation> result = service.delete(reservation);

        assertEquals(1,reservations.size());
    }
    @Test
    void shouldNotDeleteFromDatesInPast() throws DataException {
        List<Reservation> reservations = service.findResById("jsisse@gmail.com",1);
        int reservationId = 1;
        Reservation reservation = reservations.stream().filter(i -> i.getResId() == reservationId)
                .findFirst().orElse(null);

        assert reservation != null;
        Result<Reservation> result = service.delete(reservation);

        assertFalse(result.isSuccess());
        assertEquals(1,result.getErrorMessages().size());
    }

}
