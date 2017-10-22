package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ServiceItDelete extends DeleteTask {

    public ServiceItDelete() {
        super(
                "Could not delete service integration tests",
                PlasterTaskId.SERVICE_IT,
                TemplateType.SERVICE_IT,
                new ServiceTestDelete()
        );
    }

}
