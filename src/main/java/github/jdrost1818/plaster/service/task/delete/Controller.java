package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

class Controller extends DeleteTask {

    Controller() {
        super(
                "Could not delete controller",
                PlasterTaskId.CONTROLLER,
                TemplateType.CONTROLLER,
                new Service()
        );
    }

}
