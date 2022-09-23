package learn.reserving.data;

import learn.reserving.models.Guest;

import java.util.ArrayList;
import java.util.List;

public interface GuestRepository {
    ArrayList<Guest> findAll();

    Guest findByEmail(String email);

    Guest findById(int id);

}
