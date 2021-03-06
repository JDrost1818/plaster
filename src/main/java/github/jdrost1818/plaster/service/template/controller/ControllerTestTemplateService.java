package github.jdrost1818.plaster.service.template.controller;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.template.TemplateService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ControllerTestTemplateService extends TemplateService {

    public ControllerTestTemplateService(ConfigurationService configurationService) {
        super(TemplateType.CONTROLLER_TEST, configurationService);
    }

    @Override
    protected JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {

        model = super.addId(model, fileInformation);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.CONTROLLER);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.CONTROLLER_TEST);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.MODEL);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.SERVICE);

        return model;
    }

    @Override
    protected JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/controller/controllerTest.twig");
    }

}
