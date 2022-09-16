package learn.reserving.domain;

import learn.reserving.data.HostRepository;
import learn.reserving.data.HostRepositoryDouble;
import learn.reserving.models.Host;
import org.junit.jupiter.api.Test;

public class HostServiceTest {

    HostService service = new HostService(new HostRepositoryDouble());

    @Test
    void shouldFindHostByEmail() {}

    @Test
    void shouldNotFindByBlankEmail() {}

    @Test
    void shouldNotFindByNullEmail() {}

}
