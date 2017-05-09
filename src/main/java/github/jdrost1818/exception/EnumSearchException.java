package github.jdrost1818.exception;

/**
 * Created by jake on 5/9/17.
 */
public class EnumSearchException extends Exception {

    public EnumSearchException () {

    }

    public EnumSearchException (String message) {
        super (message);
    }

    public EnumSearchException (Throwable cause) {
        super (cause);
    }

    public EnumSearchException (String message, Throwable cause) {
        super (message, cause);
    }

}
