package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.DoNothing;

public class ControllerTestGenerate extends GenerateTask {

    public ControllerTestGenerate() {
        super(
                "Could not generate controller tests",
                PlasterTaskId.CONTROLLER_TEST,
                ServiceProvider.getControllerTestTemplateService(),
                new DoNothing()
        );
    }

}
