package github.jdrost1818.plaster.service.template.general;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.template.FlattenedField;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.template.TemplateService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ITDocParentTemplateService extends TemplateService {

    private final UtilityService utilityService = ServiceProvider.getUtilityService();

    public ITDocParentTemplateService(ConfigurationService configurationService) {
        super(TemplateType.IT_DOC_PARENT, configurationService);
    }

    @Override
    protected JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        return addTemplateInfo(model, fileInformation);
    }

    @Override
    protected JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/general/itDocParent.twig");
    }

    private JtwigModel addTemplateInfo(JtwigModel model, FileInformation fileInformation) {
        String fullFilePath = this.utilityService.getFilePath(fileInformation, templateType);

        String packageName = fullFilePath.split("/", 1)[0];
        String className = fullFilePath.split("/", 1)[1];

        return model.with(templateType.templateVarName, new FlattenedField(packageName, className, ""));
    }
}
