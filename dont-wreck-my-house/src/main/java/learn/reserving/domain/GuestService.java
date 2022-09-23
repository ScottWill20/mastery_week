package learn.reserving.domain;

import learn.reserving.data.GuestRepository;
import learn.reserving.models.Guest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email) {
        return repository.findByEmail(email);
    }



}
