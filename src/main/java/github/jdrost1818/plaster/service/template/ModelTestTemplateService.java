package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ModelTestTemplateService extends TemplateService {

    public ModelTestTemplateService(ConfigurationService configurationService) {
        super(configurationService);
    }

    @Override
    JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        return super.addTypeField(model, fileInformation.getClassName(), TemplateType.MODEL_TEST);
    }

    @Override
    JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/model/modelTest.twig");
    }
}
