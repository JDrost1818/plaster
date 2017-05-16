package github.jdrost1818.plaster.data;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.method.Generate;
import github.jdrost1818.plaster.method.PlasterMethod;

import java.util.List;

public enum ModeScope {

    SCAFFOLD(
            Lists.newArrayList(Generate.MODEL, Generate.CONTROLLER, Generate.SERVICE, Generate.REPOSITORY),
            Lists.newArrayList()),
    MODEL(
            Lists.newArrayList(Generate.MODEL),
            Lists.newArrayList()),
    CONTROLLER(
            Lists.newArrayList(Generate.CONTROLLER),
            Lists.newArrayList()),
    SERVICE(
            Lists.newArrayList(Generate.SERVICE),
            Lists.newArrayList()),
    REPOSITORY(
            Lists.newArrayList(Generate.REPOSITORY),
            Lists.newArrayList()),
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
