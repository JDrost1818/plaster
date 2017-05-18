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

    /**
     * {@inheritDoc}
     */
    @Override
    JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
        JtwigModel serviceModel;

        serviceModel = super.addTypeField(model, genTypeModel, TemplateType.MODEL);
        serviceModel = super.addTypeField(serviceModel, genTypeModel, TemplateType.SERVICE);
        serviceModel = super.addTypeField(serviceModel, genTypeModel, TemplateType.REPOSITORY);
        serviceModel = super.addDependencies(serviceModel, fileInformation.getId());
        serviceModel = super.addId(serviceModel, fileInformation);

        return serviceModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/service/service.twig");
    }

}