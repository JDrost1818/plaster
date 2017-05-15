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
    public JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
        model = super.addTypeField(model, genTypeModel, TemplateType.CONTROLLER);
        model = super.addTypeField(model, genTypeModel, TemplateType.MODEL);
        model = super.addTypeField(model, genTypeModel, TemplateType.SERVICE);
        model = super.addDependencies(model, fileInformation.getId());
        model = super.addId(model, fileInformation);
        model = addBaseRoute(model, fileInformation);

        return model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/controller/controller.twig");
    }

    private JtwigModel addBaseRoute(JtwigModel model, FileInformation fileInformation) {
        return model.with("baseRoute", StringUtils.uncapitalize(fileInformation.getClassName()));
    }

}