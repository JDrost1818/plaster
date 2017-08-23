package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.DoNothing;

public class ControllerDocITGenerate extends GenerateTask {

    public ControllerDocITGenerate() {
        super(
                "Could not generate controller it doc tests",
                PlasterTaskId.CONTROLLER_DOC_IT,
                ServiceProvider.getControllerDocITTemplateService(),
                new DoNothing()
        );
    }

}
