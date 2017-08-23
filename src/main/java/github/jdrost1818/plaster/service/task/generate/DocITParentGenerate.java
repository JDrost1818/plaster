package github.jdrost1818.plaster.service.task.generate;

import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTask;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class DocITParentGenerate extends GenerateTask {

    public DocITParentGenerate(PlasterTask nextTask) {
        super(
                "Could not generate integration test parent class",
                PlasterTaskId.DOC_IT_PARENT,
                ServiceProvider.getDocITParentTemplateService(),
                nextTask
        );
    }

}
