package Exceptions;

public class ReservesIOException extends Exception {

    public ReservesIOException() {
        super();

    }
    public ReservesIOException(String message) {
        super(message);

    }
    public ReservesIOException(String message, Throwable cause) {
        super(message, cause);

    }

    public ReservesIOException(Throwable cause) {
        super(cause);

    }
}
