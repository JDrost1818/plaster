package github.jdrost1818.plaster.service.task;

import github.jdrost1818.plaster.data.Mode;
import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;

public class TaskService {

    public void perform(Mode mode, ModeScope modeScope, FileInformation fileInformation) {
        PlasterTask initialTask = getInitialTask();
        PlasterTaskId lastTaskId = getLastTaskId();

        this.perform(initialTask, fileInformation, lastTaskId);
    }

    private void perform(PlasterTask task, FileInformation fileInformation, PlasterTaskId lastTaskId) {
        if (task.execute(fileInformation)) {
            if (task.taskId == lastTaskId) {
                task.finish();
            } else {
                this.perform(task.success(), fileInformation, lastTaskId);
            }
        } else {
            task.failure();
        }
    }

    private PlasterTask getInitialTask() {
        return null;
    }

    private PlasterTaskId getLastTaskId() {
        return null;
    }
}
