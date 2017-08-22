package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

class Repository extends GenerateTask {

    Repository() {
        super(
                "Could not generate repository",
                PlasterTaskId.REPOSITORY,
                ServiceProvider.getRepositoryTemplateService(),
                new Service()
        );
    }

}
