package github.jdrost1818.plaster.task;

import github.jdrost1818.plaster.data.ModeScope;

public interface TaskProvider {

    PlasterTask getInitialTask(ModeScope modeScope);

}
