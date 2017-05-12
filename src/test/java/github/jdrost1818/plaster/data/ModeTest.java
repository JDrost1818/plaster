package github.jdrost1818.plaster.data;

import github.jdrost1818.plaster.exception.PlasterException;
import org.junit.Test;

import static github.jdrost1818.plaster.data.Mode.GENERATE;
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

    @Test(expected = PlasterException.class)
    public void getMode_no_result() throws Exception {
        Mode.getMode("this will return no results");
    }

}