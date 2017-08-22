package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ServiceTestDelete extends DeleteTask {

    public ServiceTestDelete() {
        super(
                "Could not delete service tests",
                PlasterTaskId.SERVICE_TEST,
                TemplateType.SERVICE_TEST,
                new RepositoryDelete()
        );
    }

}
