package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

class Service extends DeleteTask {

    Service() {
        super(
                "Could not delete service",
                PlasterTaskId.SERVICE,
                TemplateType.SERVICE,
                new Repository()
        );
    }

}
