package github.jdrost1818.plaster;

import org.junit.Test;

public class PlasterTest {

    @Test
    public void main_invalid_arg() {
        // This test doesn't really work, I need to monitor
        // the system exit, hmm....
        Plaster.main(new String[] {});
    }

}
