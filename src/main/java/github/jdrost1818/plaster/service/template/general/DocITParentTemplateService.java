package github.jdrost1818.plaster.service.template.general;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.template.TemplateService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class DocITParentTemplateService extends TemplateService {

    public DocITParentTemplateService(ConfigurationService configurationService) {
        super(TemplateType.IT_DOC_PARENT, configurationService);
    }

    @Override
    protected JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        return addDocField(model, fileInformation);
    }

    @Override
    protected JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/general/docITParent.twig");
    }
}
