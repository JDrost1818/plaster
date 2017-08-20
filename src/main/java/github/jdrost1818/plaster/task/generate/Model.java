package github.jdrost1818.plaster.task.generate;

import github.jdrost1818.plaster.data.ModeScope;

class Model extends GenerateTask {

    public Model() {
        super(
                "Could not generate model",
                ModeScope.MODEL,
                new Repository(),
                (info) -> generateService.modifyModel(info)
        );
    }

}

