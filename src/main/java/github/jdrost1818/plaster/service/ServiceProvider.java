package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.service.modifier.EditService;
import github.jdrost1818.plaster.service.modifier.GenerateService;
import github.jdrost1818.plaster.service.template.ControllerTemplateService;
import github.jdrost1818.plaster.service.template.ModelTemplateService;
import github.jdrost1818.plaster.service.template.RepositoryTemplateService;
import github.jdrost1818.plaster.service.template.ServiceTemplateService;
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
    private static final SearchService searchService = new SearchService(configurationService);

    @Getter
    private static final DependencyService dependencyService = new DependencyService(searchService);

    @Getter
    private static final TypeService typeService = new TypeService(searchService, dependencyService);

    @Getter
    private static final FieldService fieldService = new FieldService(typeService);

    @Getter
    private static final ModelTemplateService modelTemplateService = new ModelTemplateService(configurationService);

    @Getter
    private static final ControllerTemplateService controllerTemplateService = new ControllerTemplateService(configurationService);

    @Getter
    private static final RepositoryTemplateService repositoryTemplateService = new RepositoryTemplateService(configurationService);

    @Getter
    private static final ServiceTemplateService serviceTemplateService = new ServiceTemplateService(configurationService);

    @Getter
    private static final GenerateService generateService = new GenerateService(
            configurationService,
            modelTemplateService,
            controllerTemplateService,
            serviceTemplateService,
            repositoryTemplateService
    );

    @Getter
    private static final EditService editService = new EditService();

}
