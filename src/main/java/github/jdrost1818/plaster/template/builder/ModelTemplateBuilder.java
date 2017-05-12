package github.jdrost1818.plaster.template.builder;

import github.jdrost1818.plaster.domain.Field;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.template.Template;
import github.jdrost1818.plaster.template.TemplateBuilder;
import github.jdrost1818.plaster.template.TemplateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jtwig.JtwigModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelTemplateBuilder extends TemplateBuilder {

    private static ModelTemplateBuilder instance = new ModelTemplateBuilder();

    public static ModelTemplateBuilder getInstance() {
        return instance;
    }

    @Override
    public JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
        model = TemplateUtil.addDependencies(model, fileInformation);
        model = TemplateUtil.addFields(model, fileInformation);
        model = TemplateUtil.addId(model, fileInformation);

        if (genTypeModel.isLombokEnabled()) {
            model = addLombokHeader(model);
        } else {
            model = addGettersAndSetters(model, fileInformation);
        }

        return model;
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
