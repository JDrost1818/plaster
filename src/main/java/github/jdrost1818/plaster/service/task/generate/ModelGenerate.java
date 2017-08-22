package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ModelGenerate extends GenerateTask {

    public ModelGenerate() {
        super(
                "Could not generate model",
                PlasterTaskId.MODEL,
                ServiceProvider.getModelTemplateService(),
                new RepositoryGenerate()
        );
    }

}

