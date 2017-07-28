package github.jdrost1818.plaster.task.generate;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.modifier.GenerateService;

class GenerateModel extends GenerateTask {

    protected GenerateService generateService = ServiceProvider.getGenerateService();

    public GenerateModel() {
        super("Could not generate model", ModeScope.MODEL);
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        return false;
    }

    @Override
    protected void success(FileInformation fileInformation) {

    }
}
