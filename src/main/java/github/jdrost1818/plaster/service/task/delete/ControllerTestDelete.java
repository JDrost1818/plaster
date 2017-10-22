package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ControllerTestDelete extends DeleteTask {

    public ControllerTestDelete() {
        super(
                "Could not delete controller tests",
                PlasterTaskId.CONTROLLER_TEST,
                TemplateType.CONTROLLER_TEST,
                new ServiceDelete()
        );
    }

}
