package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ServiceTestGenerate extends GenerateTask {

    public ServiceTestGenerate() {
        super(
                "Could not generate service tests",
                PlasterTaskId.SERVICE_TEST,
                ServiceProvider.getServiceTestTemplateService(),
                new ControllerGenerate()
        );
    }

}
