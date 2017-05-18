package github.jdrost1818.plaster.service.template;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.Field;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.template.Template;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class ModelTemplateService extends TemplateService {

    public ModelTemplateService(ConfigurationService configurationService) {
        super(configurationService);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
        JtwigModel modelModel;

        modelModel = super.addTypeField(model, genTypeModel, TemplateType.MODEL);
        modelModel = super.addDependencies(modelModel, fileInformation);
        modelModel = super.addFields(modelModel, fileInformation);
        modelModel = super.addId(modelModel, fileInformation);

        if (genTypeModel.isLombokEnabled()) {
            modelModel = addLombokHeader(modelModel);
        } else {
            modelModel = addGettersAndSetters(modelModel, fileInformation);
        }

        return modelModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/model/model.twig");
    }

    private JtwigModel addGettersAndSetters(JtwigModel model, FileInformation fileInformation) {
        StringBuilder methodString = new StringBuilder();
        List<Field> fields = Lists.newArrayList(fileInformation.getId());
        fields.addAll(fileInformation.getFields());

        for (Field field : fields) {
            String className = field.getTypeDeclaration().getDeclaration();
            String varName = field.getVariableName();

            methodString.append(Template.getGetterTemplate(className, varName));
            methodString.append("\n");
            methodString.append(Template.getSetterTemplate(className, varName));
            methodString.append("\n");
        }

        return model.with("methods", methodString.toString());
    }

    private JtwigModel addLombokHeader(JtwigModel model) {
        return model.with("header",
                "import lombok.AllArgsConstructor;\n" +
                "import lombok.Builder;\n" +
                "import lombok.Data;\n" +
                "import lombok.NoArgsConstructor;\n" +
                "\n" +
                "@AllArgsConstructor\n" +
                "@Builder\n" +
                "@Data\n" +
                "@NoArgsConstructor");
    }

}
