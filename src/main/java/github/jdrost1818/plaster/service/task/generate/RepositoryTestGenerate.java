package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class RepositoryTestGenerate extends GenerateTask {

    public RepositoryTestGenerate() {
        super(
                "Could not generate repository tests",
                PlasterTaskId.REPOSITORY_TEST,
                ServiceProvider.getRepositoryTestTemplateService(),
                new ServiceGenerate()
        );
    }

}
