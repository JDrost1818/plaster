package github.jdrost1818.plaster.service.task;

import github.jdrost1818.plaster.data.Mode;
import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.delete.ControllerDelete;
import github.jdrost1818.plaster.service.task.generate.ModelGenerate;

import java.util.HashMap;
import java.util.Map;

public class TaskService {

    private final ConfigurationService configurationService;

    private final Map<ModeScope, PlasterTaskId> scopeDeleteTaskMap;

    public TaskService(ConfigurationService configurationService) {
        this.configurationService = configurationService;

        this.scopeDeleteTaskMap = new HashMap<>();
        this.scopeDeleteTaskMap.put(ModeScope.MODEL, PlasterTaskId.MODEL_TEST);
        this.scopeDeleteTaskMap.put(ModeScope.REPOSITORY, PlasterTaskId.REPOSITORY_TEST);
        this.scopeDeleteTaskMap.put(ModeScope.SERVICE, PlasterTaskId.SERVICE_TEST);
        this.scopeDeleteTaskMap.put(ModeScope.CONTROLLER, PlasterTaskId.CONTROLLER_TEST);
        this.scopeDeleteTaskMap.put(ModeScope.SCAFFOLD, PlasterTaskId.MODEL_TEST);
    }

    public void perform(Mode mode, ModeScope modeScope, FileInformation fileInformation) {
        PlasterTask initialTask = getInitialTask(mode);
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

    private PlasterTask getInitialTask(Mode mode) {
        switch(mode) {
            case GENERATE:
                return new ModelGenerate();
            case DELETE:
                return new ControllerDelete();
            default:
                return null;
        }
    }

    private PlasterTaskId getLastTaskId(Mode mode, ModeScope scope) {
        switch (mode) {
            case GENERATE:
                return this.getLastGenerationTaskId(scope);
            case DELETE:
                return this.scopeDeleteTaskMap.get(scope);
            default:
                return null;
        }
    }

    private PlasterTaskId getLastGenerationTaskId(ModeScope scope) {
        boolean testsEnabled = this.configurationService.getBoolean(Setting.IS_TESTING_ENABLED);

        switch (scope) {
            case MODEL:
                return (testsEnabled) ? PlasterTaskId.MODEL_TEST : PlasterTaskId.MODEL;
            case REPOSITORY:
                return (testsEnabled) ? PlasterTaskId.REPOSITORY_TEST : PlasterTaskId.REPOSITORY;
            case SERVICE:
                return (testsEnabled) ? PlasterTaskId.SERVICE_TEST : PlasterTaskId.SERVICE;
            case CONTROLLER:
            case SCAFFOLD:
                return (testsEnabled) ? PlasterTaskId.CONTROLLER_IT : PlasterTaskId.CONTROLLER;
            default:
                throw new PlasterException("Could not find end task for scope ['" + scope + "']");
        }
    }

}
