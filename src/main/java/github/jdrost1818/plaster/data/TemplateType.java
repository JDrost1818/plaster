package github.jdrost1818.plaster.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemplateType {

    MODEL("", "modelField", Setting.REL_MODEL_PACKAGE),
    REPOSITORY("Repository", "repoField", Setting.REL_REPOSITORY_PACKAGE),
    SERVICE("Service", "serviceField", Setting.REL_SERVICE_PACKAGE),
    CONTROLLER("Controller", "controllerField", Setting.REL_CONTROLLER_PACKAGE);

    public final String suffix;
    public final String templateVarName;
    public final Setting relPathSetting;

}
