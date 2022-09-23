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

    static final String SEED_FILE_PATH = "./data/hosts-seed.csv";
    static final String TEST_FILE_PATH = "./data/hosts-test.csv";

    private  final HostFileRepository repository = new HostFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFidAll() {
        List<Host> all = repository.findAll();
        assertEquals(1000, all.size());
    }

    @Test
    void shouldFindByEmail() {
        Host yearnes = repository.findByEmail("eyearnes0@sfgate.com");
        assertNotNull(yearnes);
        assertEquals("3 Nova Trail", yearnes.getAddress());
        assertEquals(new BigDecimal("425"), yearnes.getWeekendRate());
    }

}
