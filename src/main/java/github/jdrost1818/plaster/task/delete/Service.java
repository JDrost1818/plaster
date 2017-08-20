package github.jdrost1818.plaster.task.delete;

import github.jdrost1818.plaster.data.ModeScope;

class Service extends DeleteTask {

    Service() {
        super(
                "Could not delete service",
                ModeScope.SERVICE,
                new Repository(),
                (info) -> deleteService.modifyService(info)
        );
    }

}
