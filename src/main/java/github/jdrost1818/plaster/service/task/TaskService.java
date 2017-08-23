package github.jdrost1818.plaster.service.task;

import github.jdrost1818.plaster.data.Mode;
import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.delete.ControllerDelete;
import github.jdrost1818.plaster.service.task.generate.ModelGenerate;

public class TaskService {

    private final ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    public void perform(Mode mode, ModeScope modeScope, FileInformation fileInformation) {
        PlasterTask initialTask = getInitialTask(mode, modeScope);
        PlasterTaskId lastTaskId = getLastTaskId(mode, modeScope);

        this.perform(initialTask, fileInformation, lastTaskId);
    }

    private void perform(PlasterTask task, FileInformation fileInformation, PlasterTaskId lastTaskId) {
        if (task == null) {
            return;
        }

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

    private PlasterTask getInitialTask(Mode mode, ModeScope scope) {
        switch(mode) {
            case GENERATE:
                return getInitialGenerateTask();
            case DELETE:
                return getInitialDeleteTask(scope);
        }
        return null;
    }

    private PlasterTask getInitialDeleteTask(ModeScope scope) {
        return new ControllerDelete();
    }

    private PlasterTask getInitialGenerateTask() {
        return new ModelGenerate();
    }

    private PlasterTaskId getLastTaskId(Mode mode, ModeScope scope) {
        switch (mode) {
            case GENERATE:
                return getLastGenerateTaskId(scope);
            case DELETE:
                return getLastDeleteTaskId();
        }
        return null;
    }


    private PlasterTaskId getLastGenerateTaskId(ModeScope scope) {
        return PlasterTaskId.CONTROLLER_IT;
    }

    private PlasterTaskId getLastDeleteTaskId() {
        return PlasterTaskId.MODEL;
    }
}
