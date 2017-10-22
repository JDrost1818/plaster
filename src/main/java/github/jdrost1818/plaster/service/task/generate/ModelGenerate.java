package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.CheckSetting;

public class ModelGenerate extends GenerateTask {

    public ModelGenerate() {
        super(
                "Could not generate model",
                PlasterTaskId.MODEL,
                ServiceProvider.getModelTemplateService(),
                new CheckSetting(
                        Setting.IS_TESTING_ENABLED,
                        new ModelTestGenerate(),
                        new RepositoryGenerate()
                )
        );
    }

}

