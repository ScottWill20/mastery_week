package learn.reserving.domain;

import learn.reserving.data.GuestRepositoryDouble;
import learn.reserving.models.Guest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestServiceTest {

    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldFindGuestByEmail() {
        Guest actual = service.findByEmail("swilliams@dev-10.com");

        assertNotNull(actual);
        assertEquals("Williams",actual.getLastName());
    }

    @Test
    void shouldNotFindByBlankEmail() {
       Guest actual = service.findByEmail("  ");
       assertNull(actual);
    }

    @Test
    void shouldNotFindByNullEmail() {
        Guest actual = service.findByEmail(null);
        assertNull(actual);
    }



}
