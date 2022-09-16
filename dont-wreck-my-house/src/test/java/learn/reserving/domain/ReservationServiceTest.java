package learn.reserving.domain;

import learn.reserving.data.GuestRepositoryDouble;
import learn.reserving.data.HostRepositoryDouble;
import learn.reserving.data.ReservationRepositoryDouble;
import org.junit.jupiter.api.Test;

public class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new HostRepositoryDouble(),
            new GuestRepositoryDouble());


    @Test
    void shouldFindByHostEmail() {}
    @Test
    void shouldNotFindByNullHostEmail() {}
    @Test
    void shouldNotFindByBlankHostEmail() {}
    @Test
    void shouldNotFindByNullHost() {}
    @Test
    void shouldAdd() {}
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
