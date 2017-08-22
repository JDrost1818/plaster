package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class RepositoryGenerate extends GenerateTask {

    RepositoryGenerate() {
        super(
                "Could not generate repository",
                PlasterTaskId.REPOSITORY,
                ServiceProvider.getRepositoryTemplateService(),
                new ServiceGenerate()
        );
    }

}
