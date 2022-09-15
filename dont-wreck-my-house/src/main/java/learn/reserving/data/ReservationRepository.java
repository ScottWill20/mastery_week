package learn.reserving.data;

import learn.reserving.models.Host;
import learn.reserving.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findReservationsByHost(Host host);
}
