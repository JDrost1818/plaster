package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.Field;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.template.Template;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ModelTemplateService extends TemplateService {

    public ModelTemplateService(ConfigurationService configurationService) {
        super(configurationService);
    }

    @Override
    public JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
        model = super.addTypeField(model, genTypeModel, TemplateType.MODEL);
        model = super.addDependencies(model, fileInformation);
        model = super.addFields(model, fileInformation);
        model = super.addId(model, fileInformation);

        if (genTypeModel.isLombokEnabled()) {
            model = addLombokHeader(model);
        } else {
            model = addGettersAndSetters(model, fileInformation);
        }

        return model;
    }

    @Override
    public JtwigTemplate getTemplate() {
        return JtwigTemplate.classpathTemplate("template/model/model.twig");
    }

    private JtwigModel addGettersAndSetters(JtwigModel model, FileInformation fileInformation) {
        StringBuilder methodString = new StringBuilder();

        for (Field field : fileInformation.getFields()) {
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
