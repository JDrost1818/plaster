package github.jdrost1818.plaster.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemplateType {

    MODEL("", "modelField", "model/model.twig", Setting.REL_MODEL_PACKAGE),
    REPOSITORY("Repository", "repoField", "repository/repository.twig", Setting.REL_REPOSITORY_PACKAGE),
    SERVICE("Service", "serviceField", "service/service.twig", Setting.REL_SERVICE_PACKAGE),
    CONTROLLER("Controller", "controllerField", "controller/controller.twig", Setting.REL_CONTROLLER_PACKAGE);

    public final String suffix;
    public final String templateVarName;
    public final String templateLocation;
    public final Setting relPathSetting;

}
