package github.jdrost1818.plaster.task.delete;

import github.jdrost1818.plaster.data.ModeScope;

class Model extends DeleteTask {

    public Model() {
        super(
                "Could not delete model",
                ModeScope.MODEL,
                new Repository(),
                (info) -> deleteService.modifyModel(info)
        );
    }

}

