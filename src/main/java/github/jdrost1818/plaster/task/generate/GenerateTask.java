package github.jdrost1818.plaster.task.generate;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.modifier.GenerateService;
import github.jdrost1818.plaster.task.PlasterTask;

public abstract class GenerateTask extends PlasterTask {

    public GenerateTask(String errorMsg, ModeScope scope) {
        super(errorMsg, scope);
    }

    protected static GenerateService generateService = ServiceProvider.getGenerateService();

}
