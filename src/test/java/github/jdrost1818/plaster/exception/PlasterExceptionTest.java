package github.jdrost1818.plaster.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PlasterExceptionTest {

    @Test
    public void testPlasterException_with_msg() {
        String message = "Message";
        PlasterException plasterException = new PlasterException(message);

        assertThat(plasterException.getMessage(), equalTo(message));
    }

    @Test
    public void testPlasterException_with_msg_and_throwable() {
        String message = "Message";
        Throwable error = new IllegalAccessError("");
        PlasterException plasterException = new PlasterException(message, error);

        assertThat(plasterException.getMessage(), equalTo(message));
        assertThat(plasterException.getCause(), equalTo(error));
    }

}