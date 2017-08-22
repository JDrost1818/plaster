package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class RepositoryTemplateService extends TemplateService {

    public RepositoryTemplateService(ConfigurationService configurationService) {
        super(TemplateType.REPOSITORY, configurationService);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        JtwigModel repoModel;

        repoModel = super.addTypeField(model, fileInformation.getClassName(), TemplateType.REPOSITORY);
        repoModel = super.addTypeField(repoModel, fileInformation.getClassName(), TemplateType.MODEL);
        repoModel = super.addDependencies(repoModel, fileInformation.getId());
        repoModel = super.addId(repoModel, fileInformation);

        return repoModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/repository/repository.twig");
    }

}
