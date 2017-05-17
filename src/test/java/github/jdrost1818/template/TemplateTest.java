package github.jdrost1818.template;

import github.jdrost1818.plaster.template.Template;
import github.jdrost1818.plaster.util.TestUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TemplateTest {

    @Test
    public void testCannotInstantiate() throws Exception {
        assertTrue(TestUtil.testUtilClass(Template.class));
    }

}
