package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ControllerTemplateService extends TemplateService {

    public ControllerTemplateService(ConfigurationService configurationService) {
        super(configurationService);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
        JtwigModel controllerModel;

        controllerModel = super.addTypeField(model, genTypeModel, TemplateType.CONTROLLER);
        controllerModel = super.addTypeField(controllerModel, genTypeModel, TemplateType.MODEL);
        controllerModel = super.addTypeField(controllerModel, genTypeModel, TemplateType.SERVICE);
        controllerModel = super.addDependencies(controllerModel, fileInformation.getId());
        controllerModel = super.addId(controllerModel, fileInformation);
        controllerModel = addBaseRoute(controllerModel, fileInformation);

        return controllerModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/controller/controller.twig");
    }

    private JtwigModel addBaseRoute(JtwigModel model, FileInformation fileInformation) {
        return model.with("baseRoute", StringUtils.uncapitalize(fileInformation.getClassName()));
    }

}