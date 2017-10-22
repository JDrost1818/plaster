package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ServiceDelete extends DeleteTask {

    ServiceDelete() {
        super(
                "Could not delete service",
                PlasterTaskId.SERVICE,
                TemplateType.SERVICE,
                new ServiceTestDelete()
        );
    }

}
