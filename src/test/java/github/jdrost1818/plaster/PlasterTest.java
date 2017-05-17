package github.jdrost1818.plaster;

import github.jdrost1818.plaster.util.TestUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlasterTest {

    @Test
    public void testCannotInstantiate() throws Exception {
        assertTrue(TestUtil.testUtilClass(Plaster.class));
    }

    @Test
    public void main_invalid_arg() {
        // This test doesn't really work, I need to monitor
        // the system exit, hmm....
        Plaster.main(new String[] {});
    }

}
