package github.jdrost1818.plaster.task.generate;

import github.jdrost1818.plaster.data.ModeScope;

class Repository extends GenerateTask {

    Repository() {
        super(
                "Could not generate repository",
                ModeScope.REPOSITORY,
                new Service(),
                (info) -> generateService.modifyRepository(info)
        );
    }
}
