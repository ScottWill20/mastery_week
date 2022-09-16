package learn.reserving.data;

import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HostFileRepositoryTest {

    @Test
    void shouldFindAll() {
        HostFileRepository repo = new HostFileRepository("./data/hosts.csv");
        List<Host> all = repo.findAll();
        assertEquals(1000, all.size());
    }

    @Test
    void shouldFindByEmail() {
        HostFileRepository repo = new HostFileRepository("./data/hosts.csv");
        Host yearnes = repo.findByEmail("eyearnes0@sfgate.com");
        assertNotNull(yearnes);
        assertEquals("3 Nova Trail", yearnes.getAddress());
        assertEquals(new BigDecimal("425"), yearnes.getWeekendRate());
    }

}
