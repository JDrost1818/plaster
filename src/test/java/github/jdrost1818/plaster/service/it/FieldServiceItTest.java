package github.jdrost1818.plaster.service.it;

import github.jdrost1818.plaster.service.ConfigurationServiceTest;
import github.jdrost1818.plaster.service.FieldService;
import github.jdrost1818.plaster.service.ServiceProvider;
import org.junit.Before;

public class FieldServiceItTest {

    private FieldService classUnderTest = ServiceProvider.getFieldService();

    @Before
    public void setUp() {
        ConfigurationServiceTest.transformToItService(ServiceProvider.getConfigurationService());

        this.classUnderTest.setTypeService(ServiceProvider.getTypeService());
    }
}
