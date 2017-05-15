package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ServiceTemplateService extends TemplateService {

    public ServiceTemplateService(ConfigurationService configurationService) {
        super(configurationService);
    }

    @Override
    public JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
        model = super.addTypeField(model, genTypeModel, TemplateType.MODEL);
        model = super.addTypeField(model, genTypeModel, TemplateType.SERVICE);
        model = super.addTypeField(model, genTypeModel, TemplateType.REPOSITORY);
        model = super.addDependencies(model, fileInformation.getId());
        model = super.addId(model, fileInformation);

        return model;
    }

    @Override
    public JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/service/service.twig");
    }

}