package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class RepositoryTestDelete extends DeleteTask {

    public RepositoryTestDelete() {
        super(
                "Could not delete repository tests",
                PlasterTaskId.REPOSITORY_TEST,
                TemplateType.REPOSITORY_TEST,
                new ModelDelete()
        );
    }

}
