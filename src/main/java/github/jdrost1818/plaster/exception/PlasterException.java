package github.jdrost1818.plaster.exception;

public class PlasterException extends RuntimeException {

    public PlasterException () {

    }

    public PlasterException (String message) {
        super (message);
    }

    public PlasterException (Throwable cause) {
        super (cause);
    }

    public PlasterException (String message, Throwable cause) {
        super (message, cause);
    }

}
