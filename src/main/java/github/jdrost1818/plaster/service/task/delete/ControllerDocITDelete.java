package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ControllerDocITDelete extends DeleteTask {

    public ControllerDocITDelete() {
        super(
                "Could not delete controller documentation integration test",
                PlasterTaskId.CONTROLLER_DOC_IT,
                TemplateType.CONTROLLER_DOC_IT,
                new ControllerITDelete()
        );
    }

}
