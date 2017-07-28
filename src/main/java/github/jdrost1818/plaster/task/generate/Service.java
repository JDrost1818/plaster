package github.jdrost1818.plaster.task.generate;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.task.FileExecutor;
import github.jdrost1818.plaster.task.PlasterTask;

public class Service extends GenerateTask {

    Service() {
        super(
                "Could not generate service",
                ModeScope.SERVICE,
                new Controller(),
                (info) -> generateService.modifyService(info)
        );    }

}
