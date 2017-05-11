package github.jdrost1818.plaster.service;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServiceProviderTest {

    @Test
    public void getConfigurationService() throws Exception {
        assertThat(ServiceProvider.getConfigurationService(), sameInstance(ServiceProvider.getConfigurationService()));
    }

    @Test
    public void getDeleteService() throws Exception {
        assertThat(ServiceProvider.getDeleteService(), sameInstance(ServiceProvider.getDeleteService()));
    }

    @Test
    public void getGenerateService() throws Exception {
        assertThat(ServiceProvider.getGenerateService(), sameInstance(ServiceProvider.getGenerateService()));
    }

    @Test
    public void getTypeService() throws Exception {
        assertThat(ServiceProvider.getTypeService(), sameInstance(ServiceProvider.getTypeService()));
    }

    @Test
    public void getSearchService() throws Exception {
        assertThat(ServiceProvider.getSearchService(), sameInstance(ServiceProvider.getSearchService()));
    }

    @Test
    public void getDependencyService() throws Exception {
        assertThat(ServiceProvider.getDependencyService(), sameInstance(ServiceProvider.getDependencyService()));
    }

}