package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

class Model extends GenerateTask {

    public Model() {
        super(
                "Could not generate model",
                PlasterTaskId.MODEL,
                ServiceProvider.getModelTemplateService(),
                new Repository()
        );
    }

}

