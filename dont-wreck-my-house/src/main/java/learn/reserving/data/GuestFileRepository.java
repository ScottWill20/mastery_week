package learn.reserving.data;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class GuestFileRepository implements GuestRepository {

    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;

    public GuestFileRepository(@Value("${guestFilePath:./data/guests.csv}")String filePath) {

        this.filePath = filePath;
    }

}
