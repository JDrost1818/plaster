package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import github.jdrost1818.plaster.service.task.util.CheckSetting;

public class RepositoryGenerate extends GenerateTask {

    RepositoryGenerate() {
        super(
                "Could not generate repository",
                PlasterTaskId.REPOSITORY,
                ServiceProvider.getRepositoryTemplateService(),
                new CheckSetting(
                        Setting.IS_TESTING_ENABLED,
                        new RepositoryTestGenerate(),
                        new ServiceGenerate()
                )
        );
    }

}
