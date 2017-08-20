package github.jdrost1818.plaster.task.delete;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.task.util.DoNothing;

class Model extends DeleteTask {

    public Model() {
        super(
                "Could not delete model",
                ModeScope.MODEL,
                new DoNothing(),
                (info) -> deleteService.modifyModel(info)
        );
    }

}

