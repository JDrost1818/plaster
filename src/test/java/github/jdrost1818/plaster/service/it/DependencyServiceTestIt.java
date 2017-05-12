package github.jdrost1818.plaster.service.it;

import github.jdrost1818.plaster.domain.Dependency;
import github.jdrost1818.plaster.service.ConfigurationServiceTest;
import github.jdrost1818.plaster.service.DependencyService;
import github.jdrost1818.plaster.service.ServiceProvider;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DependencyServiceTestIt {

    private DependencyService classUnderTest = ServiceProvider.getDependencyService();

    @Before
    public void setUp() {
        ConfigurationServiceTest.transformToItService(ServiceProvider.getConfigurationService());
    }


    @Test
    public void fetchDependency_stored_no_dep() {
        Dependency foundDependency = this.classUnderTest.fetchDependency("int");

        assertThat(foundDependency, nullValue());
    }

    @Test
    public void fetchDependency_stored() {
        Dependency foundDependency = this.classUnderTest.fetchDependency("list");

        assertThat(foundDependency.getPath(), equalTo("java.util.List"));
    }

    @Test
    public void fetchDependency_custom() {
        Dependency foundDependency = this.classUnderTest.fetchDependency("example");

        assertThat(foundDependency.getPath(), equalTo("com.example.app.dir1.Example"));
    }


}
