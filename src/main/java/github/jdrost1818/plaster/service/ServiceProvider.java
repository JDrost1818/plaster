package github.jdrost1818.plaster.service;

import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * A provider for all the services defined in this package.
 * Ensures a singleton patter
 */
@UtilityClass
public class ServiceProvider {

    @Getter
    private static final ConfigurationService configurationService = new ConfigurationService().load("");

    @Getter
    private static final SearchService searchService = new SearchService();

    @Getter
    private static final TypeService typeService = new TypeService();

    @Getter
    private static final DependencyService dependencyService = new DependencyService();

    @Getter
    private static final FieldService fieldService = new FieldService();

    @Getter
    private static final DeleteService deleteService = new DeleteService();

    @Getter
    private static final GenerateService generateService = new GenerateService();
}
