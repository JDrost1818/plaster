package github.jdrost1818.plaster.template.builder;

import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.template.TemplateBuilder;
import org.jtwig.JtwigModel;

public class ModelTemplateBuilder extends TemplateBuilder {

    @Override
    public JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation) {
        return model;
    }

}
