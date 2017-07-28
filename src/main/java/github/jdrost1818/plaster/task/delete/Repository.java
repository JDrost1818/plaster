package github.jdrost1818.plaster.task.delete;

import github.jdrost1818.plaster.data.ModeScope;

class Repository extends DeleteTask {

    Repository() {
        super(
                "Could not delete repository",
                ModeScope.REPOSITORY,
                new Service(),
                (info) -> deleteService.modifyRepository(info)
        );
    }
}
