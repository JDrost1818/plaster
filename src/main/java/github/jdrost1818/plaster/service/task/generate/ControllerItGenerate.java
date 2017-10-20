package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.DoNothing;

public class ControllerItGenerate extends GenerateTask {

    public ControllerItGenerate() {
        super(
                "Could not generate controller integration tests",
                PlasterTaskId.CONTROLLER_IT,
                ServiceProvider.getControllerItTemplateService(),
                new DoNothing()
        );
    }


}
