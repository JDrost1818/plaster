package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.DoNothing;

public class ModelTestDelete extends DeleteTask {

    public ModelTestDelete() {
        super(
                "Could not delete model tests",
                PlasterTaskId.MODEL_TEST,
                TemplateType.MODEL_TEST,
                new DoNothing()
        );
    }

}
