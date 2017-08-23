package github.jdrost1818.plaster.service.template.controller;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.template.TemplateService;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ControllerTemplateService extends TemplateService {

    public ControllerTemplateService(ConfigurationService configurationService) {
        super(TemplateType.CONTROLLER, configurationService);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        JtwigModel controllerModel;

        controllerModel = super.addTypeField(model, fileInformation.getClassName(), TemplateType.CONTROLLER);
        controllerModel = super.addTypeField(controllerModel, fileInformation.getClassName(), TemplateType.MODEL);
        controllerModel = super.addTypeField(controllerModel, fileInformation.getClassName(), TemplateType.SERVICE);
        controllerModel = super.addDependencies(controllerModel, fileInformation.getId());
        controllerModel = super.addId(controllerModel, fileInformation);
        controllerModel = addBaseRoute(controllerModel, fileInformation);

        return controllerModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/controller/controller.twig");
    }

    private JtwigModel addBaseRoute(JtwigModel model, FileInformation fileInformation) {
        return model.with("baseRoute", StringUtils.uncapitalize(fileInformation.getClassName()));
    }

}