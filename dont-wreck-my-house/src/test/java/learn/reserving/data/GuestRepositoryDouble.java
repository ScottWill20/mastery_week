package learn.reserving.data;

import learn.reserving.models.Guest;

import java.util.ArrayList;

public class GuestRepositoryDouble implements GuestRepository {

    private final static Guest GUEST = new Guest(1,"Scott", "Williams", "swilliams@dev-10.com","(555) 5555555", "KS");
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(GUEST);
    }
    @Override
    public ArrayList<Guest> findAll() {
        return new ArrayList<>(guests);
    }

    @Override
    public Guest findByEmail(String email) {
        return guests.stream().filter(i -> i.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    @Override
    public Guest findById(int id) {
        return findAll().stream()
                .filter(i -> i.getGuestId() == id)
                .findFirst()
                .orElse(null);
    }
}
