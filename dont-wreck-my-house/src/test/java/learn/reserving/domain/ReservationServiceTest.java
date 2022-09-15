package learn.reserving.domain;

import learn.reserving.data.GuestRepositoryDouble;
import learn.reserving.data.HostRepositoryDouble;
import learn.reserving.data.ReservationRepositoryDouble;

public class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new HostRepositoryDouble(),
            new GuestRepositoryDouble());
}
