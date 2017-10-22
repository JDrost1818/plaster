package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ModelDelete extends DeleteTask {

    public ModelDelete() {
        super(
                "Could not delete model",
                PlasterTaskId.MODEL,
                TemplateType.MODEL,
                new ModelTestDelete()
        );
    }

}

