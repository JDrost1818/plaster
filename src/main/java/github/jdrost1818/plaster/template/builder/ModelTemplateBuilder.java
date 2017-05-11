package github.jdrost1818.plaster.template.builder;

import github.jdrost1818.plaster.domain.FileInformation;
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
    public JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        model = TemplateUtil.addDependencies(model, fileInformation);
        model = TemplateUtil.addFields(model, fileInformation);
        model = TemplateUtil.addId(model, fileInformation);

        return model;
    }

}
