package learn.reserving.data;

import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(@Value("${reservationDirectory:./data/reservations}")String directory) {
        this.directory = directory;
    }


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

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findReservationsByHost(reservation.getHost());
        int nextId = getNextId(all);
        reservation.setResId(nextId);
        all.add(reservation);
        writeAll(all,reservation.getHost());
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findReservationsByHost(reservation.getHost());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getResId() == reservation.getResId()) {
                all.set(i,reservation);
                writeAll(all,reservation.getHost());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteByResId(Reservation reservation) throws DataException {
        List<Reservation> all = findReservationsByHost(reservation.getHost());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getResId() == reservation.getResId()) {
                all.remove(i);
                writeAll(all,reservation.getHost());
                return true;
            }
        }
        return false;
    }


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
        result.setTotal(new BigDecimal(fields[4]));

        Guest guest = new Guest();
        result.setGuest(guest);
        guest.setGuestId(Integer.parseInt(fields[3]));

        return result;
    }


    private int getNextId(List<Reservation> reservations) {
        int maxId = 0;
        for (Reservation reservation : reservations) {
            if (maxId < reservation.getResId()) {
                maxId = reservation.getResId();
            }
        }
        return maxId + 1;
    }


}
