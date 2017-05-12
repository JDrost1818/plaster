package github.jdrost1818.plaster.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemplateType {

    MODEL("", "modelField"),
    REPOSITORY("Repository", "repoField"),
    SERVICE("Service", "serviceField"),
    CONTROLLER("Controller", "controllerField");

    public final String suffix;
    public final String templateVarName;

}
