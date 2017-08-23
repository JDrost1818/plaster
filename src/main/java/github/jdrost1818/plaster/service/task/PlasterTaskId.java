package github.jdrost1818.plaster.service.task;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PlasterTaskId {

    MODEL(""),
    MODEL_TEST(""),
    REPOSITORY(""),
    REPOSITORY_TEST(""),
    SERVICE(""),
    SERVICE_TEST(""),
    SERVICE_IT(""),
    CONTROLLER(""),
    CONTROLLER_TEST(""),
    CONTROLLER_IT(""),
    CONTROLLER_DOC_IT(""),
    DOC_IT_PARENT("");

    public final String description;
}
