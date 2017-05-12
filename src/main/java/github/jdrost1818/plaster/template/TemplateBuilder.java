package github.jdrost1818.plaster.template;

import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.OutputStream;

public abstract class TemplateBuilder {

    public final void renderTemplate(String templateLocation, FileInformation fileInformation,
                                     GenTypeModel genTypeModel, OutputStream out) {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/" + templateLocation);

        JtwigModel model = JtwigModel.newModel()
                .with("package", genTypeModel.getPackageName())
                .with("className", genTypeModel.getClassName());
        model = addCustomInformation(model, fileInformation);

        template.render(model, out);
    }

    public abstract JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation);

}
