package github.jdrost1818.service;

import lombok.Getter;

public class ServiceProvider {

    @Getter
    private static final ConfigurationService configurationService = new ConfigurationService();

    @Getter
    private static final DeleteService deleteService = new DeleteService();

    @Getter
    private static final GenerateService generateService = new GenerateService();

    @Getter
    private static final TypeService typeService = new TypeService();

    @Getter
    private static final SearchService searchService = new SearchService();

    @Getter
    private static final DependencyService dependencyService = new DependencyService();

}
