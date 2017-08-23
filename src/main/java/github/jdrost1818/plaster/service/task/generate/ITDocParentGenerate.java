package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTask;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class ITDocParentGenerate extends GenerateTask {

    public ITDocParentGenerate(PlasterTask nextTask) {
        super(
                "Could not generate integration test parent class",
                PlasterTaskId.IT_DOC_PARENT,
                ServiceProvider.getItDocParentTemplateService(),
                nextTask
        );
    }

}
