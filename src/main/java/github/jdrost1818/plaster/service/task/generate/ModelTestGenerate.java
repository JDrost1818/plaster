package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ModelTestGenerate extends GenerateTask {

    public ModelTestGenerate() {
        super(
                "Could not generate model tests",
                PlasterTaskId.MODEL_TEST,
                ServiceProvider.getModelTestTemplateService(),
                new RepositoryGenerate()
        );
    }

}
