package learn.reserving.domain;

import learn.reserving.data.*;
import learn.reserving.models.Guest;
import learn.reserving.models.Reservation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReservationServiceTest {


    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new HostRepositoryDouble(),
            new GuestRepositoryDouble());


    @Test
    void shouldFindByHostEmail() {
        List<Reservation> actual = service.findReservationsByHost("jsisse@gmail.com");
        assertNotNull(actual);
        assertEquals(1,actual.size());
    }
    @Test
    void shouldNotFindByNullHostEmail() {}
    @Test
    void shouldNotFindByBlankHostEmail() {}
    @Test
    void shouldNotFindByNullHost() {}
    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setCheckIn(LocalDate.of(2024,1,1));
        reservation.setCheckOut(LocalDate.of(2024,1,10));
        reservation.setGuest(new Guest(1,"Scott","Williams","swilliams@dev-10.com","913 907-5762", "KS"));
        reservation.setTotal();

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(new BigDecimal(1),result.getPayload().getTotal());
    }
    @Test
    void shouldValidateDuplicateDates() {}
    @Test
    void shouldCalculateTotal() {}
    @Test
    void shouldUpdateExisting() {}
    @Test
    void shouldNotUpdateNonExisting() {}
    @Test
    void shouldNotAddWithStartDateBeforePresentDay() {}
    @Test
    void shouldDelete() {}
    @Test
    void shouldNotDeleteFromDatesInPast(){}

}
