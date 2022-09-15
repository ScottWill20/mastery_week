package learn.reserving.data;

import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(@Value("${reservationDirectory:./data/reservations}")String directory) {
        this.directory = directory;
    }

    // findReservationsByHost


    @Override
    public List<Reservation> findReservationsByHost(Host host) {
        ArrayList<Reservation> result = new ArrayList<>();

        if (host == null || host.getHostId().isBlank() || host.getHostId() == null) {
            return result;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host)))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields,host));
                }
            }
        } catch (IOException ignored) {
        }
        return result;
    }

//    @Override
//    public Reservation add(Reservation reservation) {
//        List<Reservation> all = findByUUID(reservation.getHost().getHostId());
//    }


    private String getFilePath(Host host) {
        return Paths.get(directory, host.getHostId() + ".csv").toString();
    }

    private void writeAll(List<Reservation> reservations, Host host) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host))) {

            writer.println(HEADER);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getResId(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                reservation.getGuest().getGuestId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, Host host) {
        Reservation result = new Reservation();
        result.setResId(Integer.parseInt(fields[0]));
        result.setHost(host);
        result.setCheckIn(LocalDate.parse(fields[1]));
        result.setCheckOut(LocalDate.parse(fields[2]));
        result.setTotal();

        Guest guest = new Guest();
        result.setGuest(guest);
        guest.setGuestId(Integer.parseInt(fields[3]));

        return result;
    }


}
