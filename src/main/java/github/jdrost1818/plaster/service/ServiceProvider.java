package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.service.task.TaskService;
import github.jdrost1818.plaster.service.template.controller.ControllerItTemplateService;
import github.jdrost1818.plaster.service.template.controller.ControllerTemplateService;
import github.jdrost1818.plaster.service.template.controller.ControllerTestTemplateService;
import github.jdrost1818.plaster.service.template.model.ModelTemplateService;
import github.jdrost1818.plaster.service.template.model.ModelTestTemplateService;
import github.jdrost1818.plaster.service.template.repository.RepositoryTemplateService;
import github.jdrost1818.plaster.service.template.repository.RepositoryTestTemplateService;
import github.jdrost1818.plaster.service.template.service.ServiceTemplateService;
import github.jdrost1818.plaster.service.template.service.ServiceTestTemplateService;
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
    private static final UtilityService utilityService = new UtilityService(configurationService);

    @Getter
    private static final SearchService searchService = new SearchService(configurationService);

    @Getter
    private static final DependencyService dependencyService = new DependencyService(searchService);

    @Getter
    private static final TypeService typeService = new TypeService(searchService, dependencyService);

    @Getter
    private static final FieldService fieldService = new FieldService(typeService);

    @Getter
    private static final TaskService taskService = new TaskService(configurationService);

    @Getter
    private static final ModelTemplateService modelTemplateService = new ModelTemplateService(configurationService);

    @Getter
    private static final ModelTestTemplateService modelTestTemplateService = new ModelTestTemplateService(configurationService);

    @Getter
    private static final ControllerTemplateService controllerTemplateService = new ControllerTemplateService(configurationService);

    @Getter
    private static final ControllerTestTemplateService controllerTestTemplateService = new ControllerTestTemplateService(configurationService);

    @Getter
    private static final ControllerItTemplateService controllerItTemplateService = new ControllerItTemplateService(configurationService);

    @Getter
    private static final RepositoryTemplateService repositoryTemplateService = new RepositoryTemplateService(configurationService);

    @Getter
    private static final RepositoryTestTemplateService repositoryTestTemplateService = new RepositoryTestTemplateService(configurationService);

    @Getter
    private static final ServiceTemplateService serviceTemplateService = new ServiceTemplateService(configurationService);

    @Getter
    private static final ServiceTestTemplateService serviceTestTemplateService = new ServiceTestTemplateService(configurationService);

}
