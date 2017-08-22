package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.CheckSetting;

public class ServiceGenerate extends GenerateTask {

    ServiceGenerate() {
        super(
                "Could not generate service",
                PlasterTaskId.SERVICE,
                ServiceProvider.getServiceTemplateService(),
                new CheckSetting(
                        Setting.IS_TESTING_ENABLED,
                        new ServiceTestGenerate(),
                        new ControllerGenerate()
                )
        );
    }

}
