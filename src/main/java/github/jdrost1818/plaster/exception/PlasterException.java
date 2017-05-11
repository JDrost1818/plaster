package github.jdrost1818.plaster.exception;

/**
 * Exception to be thrown in situations when the input
 * to the application has caused a failure. AKA user error,
 * not programmer error
 */
public class PlasterException extends RuntimeException {

    public PlasterException (String message) {
        super (message);
    }

    public PlasterException (String message, Throwable cause) {
        super (message, cause);
    }

}
