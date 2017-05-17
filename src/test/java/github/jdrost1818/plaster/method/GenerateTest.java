package github.jdrost1818.plaster.method;

import github.jdrost1818.plaster.util.TestUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GenerateTest {

    @Test
    public void testCannotInstantiate() throws Exception {
        assertTrue(TestUtil.testUtilClass(Modify.class));
    }

}
