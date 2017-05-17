package github.jdrost1818.plaster.data;

import github.jdrost1818.plaster.util.TestUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RegexTest {

    @Test
    public void testCannotInstantiate() throws Exception {
        assertTrue(TestUtil.testUtilClass(Regex.class));
    }

}
