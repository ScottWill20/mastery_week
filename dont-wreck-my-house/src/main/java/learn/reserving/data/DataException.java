package learn.reserving.data;

import java.io.FileNotFoundException;

public class DataException extends Throwable {
    public DataException(String message) {
        super(message);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
