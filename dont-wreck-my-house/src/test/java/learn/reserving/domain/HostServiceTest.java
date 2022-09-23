package learn.reserving.domain;

import learn.reserving.data.HostRepository;
import learn.reserving.data.HostRepositoryDouble;
import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HostServiceTest {

    HostService service = new HostService(new HostRepositoryDouble());

    @Test
    void shouldFindHostByEmail() {
        Host actual = service.findByEmail("jsisse@gmail.com");
        assertNotNull(actual);
        assertEquals("Sisse",actual.getLastName());
    }

    @Test
    void shouldNotFindByBlankEmail() {
        Host actual = service.findByEmail("  ");
        assertNull(actual);
    }

    @Test
    void shouldNotFindByNullEmail() {
        Host actual = service.findByEmail(null);
        assertNull(actual);
    }

}
