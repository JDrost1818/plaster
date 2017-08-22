package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

class Service extends GenerateTask {

    Service() {
        super(
                "Could not generate service",
                PlasterTaskId.SERVICE,
                ServiceProvider.getServiceTemplateService(),
                new Controller()
        );
    }

}
