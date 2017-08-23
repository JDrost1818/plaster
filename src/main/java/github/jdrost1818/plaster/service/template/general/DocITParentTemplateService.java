package github.jdrost1818.plaster.service.template.general;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.template.FlattenedField;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.template.TemplateService;
import github.jdrost1818.plaster.util.PathUtil;
import org.apache.commons.io.FilenameUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.net.FileNameMap;

public class DocITParentTemplateService extends TemplateService {

    private final UtilityService utilityService = ServiceProvider.getUtilityService();

    public DocITParentTemplateService(ConfigurationService configurationService) {
        super(TemplateType.IT_DOC_PARENT, configurationService);
    }

    @Override
    protected JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        return addTemplateInfo(model, fileInformation);
    }

    @Override
    protected JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/general/docITParent.twig");
    }

    private JtwigModel addTemplateInfo(JtwigModel model, FileInformation fileInformation) {
        String fullFilePath = this.utilityService.getFilePath(fileInformation, templateType);

        String packageName = PathUtil.pathToPackage(FilenameUtils.getPath(fullFilePath));
        String className = FilenameUtils.getName(fullFilePath).replace(".java", "");

        return model.with(templateType.templateVarName, new FlattenedField(packageName, className, ""));
    }
}
