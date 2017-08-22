package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.DoNothing;

class Controller extends GenerateTask {

    Controller() {
        super(
                "Could not generate controller",
                PlasterTaskId.CONTROLLER,
                ServiceProvider.getControllerTemplateService(),
                new DoNothing()
        );
    }

}
