package github.jdrost1818.data;

import org.junit.Test;

import java.util.NoSuchElementException;

import static github.jdrost1818.data.Mode.DELETE;
import static github.jdrost1818.data.Mode.GENERATE;
import static github.jdrost1818.data.ModeScope.CONTROLLER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModeTest {

    @Test(expected = IllegalArgumentException.class)
    public void getMode_null() {
        Mode.getMode(null);
    }

    @Test
    public void getMode_WeiRD_CasES() {
        Mode foundMode = Mode.getMode("geNErate");

        assertThat(foundMode, equalTo(GENERATE));
    }

    @Test(expected = NoSuchElementException.class)
    public void getMode_no_result() {
        Mode.getMode("this will return no results");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getScope_scope_does_not_exist() {
        GENERATE.getScope("this does not exist");
    }

    @Test(expected = NullPointerException.class)
    public void getScope_null() {
        GENERATE.getScope(null);
    }

    @Test
    public void getScope_WeiRD_CasES() {
        assertThat(GENERATE.getScope("conTROLler"), equalTo(CONTROLLER));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getScope_unsupported_yet_exists() {
        DELETE.getScope("FIELDS");
    }

}