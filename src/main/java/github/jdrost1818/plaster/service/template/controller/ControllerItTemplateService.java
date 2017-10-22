package github.jdrost1818.plaster.service.template.controller;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.template.TemplateService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ControllerItTemplateService extends TemplateService {

    public ControllerItTemplateService(ConfigurationService configurationService) {
        super(TemplateType.CONTROLLER_IT, configurationService);
    }

    @Override
    protected JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        model = super.addId(model, fileInformation);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.CONTROLLER);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.CONTROLLER_IT);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.MODEL);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.SERVICE);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.REPOSITORY);

        return model;    }

    @Override
    protected JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/controller/controllerIT.twig");
    }
}
