package learn.reserving.data;

import learn.reserving.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GuestFileRepositoryTest {

    @Test
    void shouldFindAll() {
        GuestFileRepository repository = new GuestFileRepository("./data/guests.csv");
        List<Guest> all = repository.findAll();
        assertEquals(1000,all.size());
    }

    @Test
    void shouldFindSullivanLomas() {
        GuestFileRepository repository = new GuestFileRepository("./data/guests.csv");
        Guest sully = repository.findByEmail("slomas0@mediafire.com");
        assertNotNull(sully);
        assertEquals("Sullivan",sully.getFirstName());
        assertEquals("NV", sully.getState());
    }
}
