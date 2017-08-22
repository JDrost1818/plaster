package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ServiceTestTemplateService extends TemplateService {

    public ServiceTestTemplateService(ConfigurationService configurationService) {
        super(configurationService);
    }

    @Override
    JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.SERVICE);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.SERVICE_TEST);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.MODEL);
        model = super.addTypeField(model, fileInformation.getClassName(), TemplateType.REPOSITORY);

        return model;
    }

    @Override
    JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/service/serviceTest.twig");
    }
}
