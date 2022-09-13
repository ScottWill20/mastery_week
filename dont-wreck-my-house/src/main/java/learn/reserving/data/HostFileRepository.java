package learn.reserving.data;

import learn.reserving.models.Guest;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepository implements HostRepository {

    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String filePath;

    public HostFileRepository(@Value("${hostFilePath:./data/hosts.csv}")String filePath) {

        this.filePath = filePath;
    }

}
