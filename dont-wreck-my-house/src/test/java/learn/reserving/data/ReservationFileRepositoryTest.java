package learn.reserving.data;

import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservation-seed.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";
    static final int RESERVATION_COUNT = 12;

    final Host host = new Host();


    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);


    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findReservationsByHost() {
        host.setHostId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        List<Reservation> reservations = repository.findReservationsByHost(host);
        assertEquals(RESERVATION_COUNT,reservations.size());
    }


    @Test
    void shouldAddReservation() throws DataException {
        Reservation reservation = new Reservation();

        reservation.setCheckIn(LocalDate.of(2022,8,10));
        reservation.setCheckOut(LocalDate.of(2022,8,15));


        Guest guest = new Guest();
        guest.setGuestId(500);
        reservation.setGuest(guest);

        host.setHostId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        host.setStandardRate(new BigDecimal(200));
        host.setWeekendRate(new BigDecimal(250));
        reservation.setHost(host);

        reservation.setTotal();

        reservation = repository.add(reservation);

        assertEquals(new BigDecimal(1300),reservation.getTotal());



    }

}