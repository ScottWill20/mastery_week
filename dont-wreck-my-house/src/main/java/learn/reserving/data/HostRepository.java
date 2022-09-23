package learn.reserving.data;

import learn.reserving.models.Guest;
import learn.reserving.models.Host;

import java.util.ArrayList;
import java.util.List;

public interface HostRepository {
    ArrayList<Host> findAll();

    Host findByEmail(String email);

    Host findById(String id);
}
