package github.jdrost1818.plaster.data;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.method.Modify;
import github.jdrost1818.plaster.method.PlasterMethod;

import java.util.List;

public enum ModeScope {

    SCAFFOLD(
            Lists.newArrayList(Modify.MODEL, Modify.CONTROLLER, Modify.SERVICE, Modify.REPOSITORY)),
    MODEL(
            Lists.newArrayList(Modify.MODEL)),
    CONTROLLER(
            Lists.newArrayList(Modify.CONTROLLER)),
    SERVICE(
            Lists.newArrayList(Modify.SERVICE)),
    REPOSITORY(
            Lists.newArrayList(Modify.REPOSITORY)),
    FIELDS(
            Lists.newArrayList(Modify.FIELDS));

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
                throw new PlasterException("A developer messed up. Please contact them, yell at them, and make them buy you things");
            }
        }
    }
}
