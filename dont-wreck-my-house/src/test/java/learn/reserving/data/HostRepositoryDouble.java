package learn.reserving.data;

import learn.reserving.models.Host;
import learn.reserving.models.Reservation;

import java.math.BigDecimal;
import java.util.ArrayList;

public class HostRepositoryDouble implements HostRepository {
    public final static Host HOST = makeHost();
    public final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() {
        hosts.add(HOST);
    }
    @Override
    public ArrayList<Host> findAll() {
        return hosts;
    }

    @Override
    public Host findByEmail(String email) {
        return hosts.stream().filter(i -> i.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    @Override
    public Host findById(String id) {
        return hosts.stream().filter(i -> i.getHostId().equals(id))
                .findFirst().orElse(null);
    }

    public static Host makeHost() {
        Host host = new Host();
        host.setHostId("0e4707f4-407e-4ec9-9665-baca0aabe88c");
        host.setLastName("Sisse");
        host.setEmail("jsisse@gmail.com");
        host.setPhone("(555) 5555555");
        host.setAddress("1122 Boogie Woogie Ave");
        host.setState("CA");
        host.setZipCode(12345);
        host.setStandardRate(BigDecimal.valueOf(50));
        host.setWeekendRate(BigDecimal.valueOf(100));
        return host;
    }
}
