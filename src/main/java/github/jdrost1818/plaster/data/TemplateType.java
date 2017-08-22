package github.jdrost1818.plaster.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemplateType {

    MODEL(
            "",
            "modelField",
            "model/model.twig",
            Setting.REL_MODEL_PACKAGE
    ),
    REPOSITORY(
            "Repository",
            "repoField",
            "repository/repository.twig",
            Setting.REL_REPOSITORY_PACKAGE
    ),
    SERVICE(
            "Service",
            "serviceField",
            "service/service.twig",
            Setting.REL_SERVICE_PACKAGE
    ),
    CONTROLLER(
            "Controller",
            "controllerField",
            "controller/controller.twig",
            Setting.REL_CONTROLLER_PACKAGE
    ),
    MODEL_TEST(
            "Test",
            "modelTestField",
            "model/modelTest.twig",
            Setting.REL_MODEL_PACKAGE,
            true
    ),
    REPOSITORY_TEST(
            "RepositoryTest",
            "repoField",
            "repo/repositoryTest.twig",
            Setting.REL_REPOSITORY_PACKAGE,
            true
    ),
    SERVICE_TEST(
            "ServiceTest",
            "serviceField",
            "service/serviceTest.twig",
            Setting.REL_SERVICE_PACKAGE,
            true
    ),
    CONTROLLER_TEST(
            "ControllerTest",
            "controllerField",
            "controller/controllerTest.twig",
            Setting.REL_CONTROLLER_PACKAGE,
            true
    );

    public final String suffix;
    public final String templateVarName;
    public final String templateLocation;
    public final Setting relPathSetting;
    public final Boolean isTest;

    TemplateType(String suffix, String templateVarName, String templateLocation, Setting relPathSetting) {
        this(suffix, templateVarName, templateLocation, relPathSetting, false);
    }

}
