package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

class Repository extends DeleteTask {

    Repository() {
        super(
                "Could not delete repository",
                PlasterTaskId.REPOSITORY,
                TemplateType.REPOSITORY,
                new Model()
        );
    }

}
