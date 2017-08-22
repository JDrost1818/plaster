package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ServiceTemplateService extends TemplateService {

    public ServiceTemplateService(ConfigurationService configurationService) {
        super(TemplateType.SERVICE, configurationService);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        JtwigModel serviceModel;

        serviceModel = super.addTypeField(model, fileInformation.getClassName(), TemplateType.MODEL);
        serviceModel = super.addTypeField(serviceModel, fileInformation.getClassName(), TemplateType.SERVICE);
        serviceModel = super.addTypeField(serviceModel, fileInformation.getClassName(), TemplateType.REPOSITORY);
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