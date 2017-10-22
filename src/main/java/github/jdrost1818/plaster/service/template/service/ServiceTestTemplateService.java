package github.jdrost1818.plaster.service.template.service;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.template.TemplateService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ServiceTestTemplateService extends TemplateService {

    public ServiceTestTemplateService(ConfigurationService configurationService) {
        super(TemplateType.SERVICE_TEST, configurationService);
    }

    @Override
    protected JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {

        model = super.addId(model, fileInformation);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.SERVICE);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.SERVICE_TEST);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.MODEL);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.REPOSITORY);

        return model;
    }

    @Override
    protected JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/service/serviceTest.twig");
    }
}
