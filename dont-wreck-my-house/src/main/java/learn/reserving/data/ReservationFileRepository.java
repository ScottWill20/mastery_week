package learn.reserving.data;

import org.springframework.beans.factory.annotation.Value;

public class ReservationFileRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(@Value("${reservationDirectory:./data/reservations}")String directory) {
        this.directory = directory;
    }

}
