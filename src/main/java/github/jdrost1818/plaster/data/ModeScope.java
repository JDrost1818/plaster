package github.jdrost1818.plaster.data;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.DeveloperException;
import github.jdrost1818.plaster.method.Modify;
import github.jdrost1818.plaster.method.PlasterMethod;

import java.util.List;

public enum ModeScope {

    SCAFFOLD(
            Lists.newArrayList(Modify.MODEL, Modify.REPOSITORY, Modify.SERVICE, Modify.CONTROLLER)),
    MODEL(
            Lists.newArrayList(Modify.MODEL)),
    REPOSITORY(
            Lists.newArrayList(Modify.MODEL, Modify.REPOSITORY)),
    SERVICE(
            Lists.newArrayList(Modify.MODEL, Modify.REPOSITORY, Modify.SERVICE)),
    CONTROLLER(
            Lists.newArrayList(Modify.MODEL, Modify.REPOSITORY, Modify.SERVICE, Modify.CONTROLLER));

    private final List<PlasterMethod> modActions;
    ModeScope(List<PlasterMethod> modActions) {
        this.modActions = modActions;
    }

    @SuppressWarnings("unchecked")
    public void modify(Object service, FileInformation fileInformation) {
        for (PlasterMethod modAction : modActions) {
            try{
                modAction.perform(service, fileInformation);
            } catch (ClassCastException e) {
                throw new DeveloperException();
            }
        }
    }
}
