package github.jdrost1818.plaster.service.template.general;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.template.TemplateService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ITDocParentTemplateService extends TemplateService {


    public ITDocParentTemplateService(ConfigurationService configurationService) {
        super(null, configurationService);
    }

    @Override
    protected JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        return null;
    }

    @Override
    protected JtwigTemplate getTemplate() {
        return null;
    }
}
