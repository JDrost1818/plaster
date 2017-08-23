package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.DoNothing;

public class ControllerITDocGenerate extends GenerateTask {

    public ControllerITDocGenerate() {
        super(
                "Could not generate controller it doc tests",
                PlasterTaskId.CONTROLLER_IT_DOC,
                ServiceProvider.getControllerTestTemplateService(),
                new DoNothing()
        );
    }

}
