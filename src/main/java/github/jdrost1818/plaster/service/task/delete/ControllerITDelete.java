package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ControllerITDelete extends DeleteTask {

    public ControllerITDelete() {
        super(
                "Could not delete controller integration test",
                PlasterTaskId.CONTROLLER_IT,
                TemplateType.CONTROLLER_IT,
                new ControllerTestDelete()
        );
    }

}
