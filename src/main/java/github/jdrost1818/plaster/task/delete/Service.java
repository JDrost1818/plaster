package github.jdrost1818.plaster.task.delete;

import github.jdrost1818.plaster.data.ModeScope;

public class Service extends DeleteTask {

    Service() {
        super(
                "Could not delete service",
                ModeScope.SERVICE,
                new Controller(),
                (info) -> deleteService.modifyService(info)
        );
    }

}
