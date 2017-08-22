package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.DoNothing;

class Model extends DeleteTask {

    public Model() {
        super(
                "Could not delete model",
                PlasterTaskId.MODEL,
                TemplateType.MODEL,
                new DoNothing()
        );
    }

}

