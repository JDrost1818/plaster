package github.jdrost1818.data;

import com.google.common.collect.Lists;
import github.jdrost1818.domain.FileInformation;
import github.jdrost1818.methods.Delete;
import github.jdrost1818.methods.Generate;
import github.jdrost1818.methods.PlasterMethod;

import java.util.List;

public enum ModeScope {

    SCAFFOLD(
            Lists.newArrayList(Generate.MODEL, Generate.CONTROLLER, Generate.SERVICE, Generate.REPOSITORY),
            Lists.newArrayList(Delete.MODEL, Delete.CONTROLLER, Delete.SERVICE, Delete.REPOSITORY)),
    MODEL(
            Lists.newArrayList(Generate.MODEL),
            Lists.newArrayList(Delete.MODEL)),
    CONTROLLER(
            Lists.newArrayList(Generate.CONTROLLER),
            Lists.newArrayList(Delete.CONTROLLER)),
    SERVICE(
            Lists.newArrayList(Generate.SERVICE),
            Lists.newArrayList(Delete.SERVICE)),
    REPOSITORY(
            Lists.newArrayList(Generate.REPOSITORY),
            Lists.newArrayList(Delete.REPOSITORY)),
    FIELDS(
            Lists.newArrayList(Generate.FIELDS),
            Lists.newArrayList());

    private final List<PlasterMethod> genActions;
    private final List<PlasterMethod> delActions;
    ModeScope(List<PlasterMethod> genActions, List<PlasterMethod> delActions) {
        this.genActions = genActions;
        this.delActions = delActions;
    }

    public void generate(FileInformation fileInformation) {
        for (PlasterMethod genAction : genActions) {
            genAction.perform(fileInformation);
        }
    }

    public void delete(FileInformation fileInformation) {
        for (PlasterMethod delAction : delActions) {
            delAction.perform(fileInformation);
        }
    }
}
