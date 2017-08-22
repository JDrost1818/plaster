package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.service.task.PlasterTaskId;

public class RepositoryDelete extends DeleteTask {

    RepositoryDelete() {
        super(
                "Could not delete repository",
                PlasterTaskId.REPOSITORY,
                TemplateType.REPOSITORY,
                new ModelDelete()
        );
    }

}
