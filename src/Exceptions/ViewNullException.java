package Exceptions;

public class ViewNullException extends Exception {
    public ViewNullException() {
        super();

    }
    public ViewNullException(String message) {
        super(message);

    }
    public ViewNullException(String message, Throwable cause) {
        super(message, cause);

    }

    public ViewNullException(Throwable cause) {
        super(cause);

    }
}
