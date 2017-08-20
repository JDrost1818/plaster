package github.jdrost1818.plaster.task.delete;

import github.jdrost1818.plaster.data.ModeScope;

class Controller extends DeleteTask {

    Controller() {
        super(
                "Could not delete controller",
                ModeScope.CONTROLLER,
                new Service(),
                (info) -> deleteService.modifyController(info)
        );
    }

}
