package github.jdrost1818.plaster.service.template.repository;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.template.TemplateService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class RepositoryTestTemplateService extends TemplateService {

    public RepositoryTestTemplateService(ConfigurationService configurationService) {
        super(TemplateType.REPOSITORY_TEST, configurationService);
    }

    @Override
    protected JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        return super.addTypeField(model, fileInformation.getClassName(), TemplateType.REPOSITORY_TEST);
    }

    @Override
    protected JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/repository/repositoryTest.twig");
    }

}
