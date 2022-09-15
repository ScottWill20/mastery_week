package learn.reserving.domain;

import learn.reserving.data.HostRepository;
import learn.reserving.data.HostRepositoryDouble;
import learn.reserving.models.Host;

public class HostServiceTest {

    HostService service = new HostService(new HostRepositoryDouble());
}
