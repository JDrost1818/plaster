package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ServiceGenerate extends GenerateTask {

    ServiceGenerate() {
        super(
                "Could not generate service",
                PlasterTaskId.SERVICE,
                ServiceProvider.getServiceTemplateService(),
                new ControllerGenerate()
        );
    }

}
