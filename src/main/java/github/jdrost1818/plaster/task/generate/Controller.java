package github.jdrost1818.plaster.task.generate;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.task.util.DoNothing;

class Controller extends GenerateTask {

    Controller() {
        super(
                "Could not generate controller",
                ModeScope.CONTROLLER,
                new DoNothing(),
                (info) -> generateService.modifyRepository(info)
        );
    }

}
