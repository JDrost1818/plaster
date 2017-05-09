package github.jdrost1818.data;

import github.jdrost1818.exception.EnumSearchException;
import org.junit.Test;

import java.util.NoSuchElementException;

import static github.jdrost1818.data.Mode.DELETE;
import static github.jdrost1818.data.Mode.GENERATE;
import static github.jdrost1818.data.ModeScope.CONTROLLER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModeTest {

    @Test(expected = IllegalArgumentException.class)
    public void getMode_null() throws Exception {
        Mode.getMode(null);
    }

    @Test
    public void getMode_WeiRD_CasES() throws Exception {
        Mode foundMode = Mode.getMode("geNErate");

        assertThat(foundMode, equalTo(GENERATE));
    }

    @Test(expected = EnumSearchException.class)
    public void getMode_no_result() throws Exception {
        Mode.getMode("this will return no results");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getScope_scope_does_not_exist() throws Exception {
        GENERATE.getScope("this does not exist");
    }

    @Test(expected = NullPointerException.class)
    public void getScope_null() throws Exception {
        GENERATE.getScope(null);
    }

    @Test
    public void getScope_WeiRD_CasES() throws Exception {
        assertThat(GENERATE.getScope("conTROLler"), equalTo(CONTROLLER));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getScope_unsupported_yet_exists() throws Exception {
        DELETE.getScope("FIELDS");
    }

}