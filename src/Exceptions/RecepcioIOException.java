package Exceptions;

public class RecepcioIOException extends Exception{

    public RecepcioIOException() {
        super();

    }
    public RecepcioIOException(String message) {
        super(message);

    }
    public RecepcioIOException(String message, Throwable cause) {
        super(message, cause);

    }

    public RecepcioIOException(Throwable cause) {
        super(cause);

    }
}
