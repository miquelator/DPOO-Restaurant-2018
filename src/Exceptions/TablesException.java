package Exceptions;

public class TablesException extends Exception{

    public TablesException() {
        super();

    }
    public TablesException(String message) {
        super(message);

    }
    public TablesException(String message, Throwable cause) {
        super(message, cause);

    }

    public TablesException(Throwable cause) {
        super(cause);

    }
}
