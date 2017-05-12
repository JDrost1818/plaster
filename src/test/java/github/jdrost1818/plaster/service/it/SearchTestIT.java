package github.jdrost1818.plaster.service.it;

import github.jdrost1818.plaster.service.ConfigurationServiceTest;
import github.jdrost1818.plaster.service.SearchService;
import github.jdrost1818.plaster.service.ServiceProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class SearchTestIT {

    private SearchService classUnderTest = ServiceProvider.getSearchService();

    @Before
    public void setUp() {
        ConfigurationServiceTest.transformToItService(ServiceProvider.getConfigurationService());
    }

    @Test
    public void findClassWithName() {
        List<String> filesFound = this.classUnderTest.findClassesWithName("duplicate");

        assertThat(filesFound, hasSize(2));
    }

}
