package learn.reserving.domain;

import learn.reserving.data.HostRepository;
import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import org.springframework.stereotype.Service;

@Service
public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
