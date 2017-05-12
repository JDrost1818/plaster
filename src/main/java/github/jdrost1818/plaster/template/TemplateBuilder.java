package github.jdrost1818.plaster.template;

import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public abstract class TemplateBuilder {

    public final void renderTemplate(String templateLocation, FileInformation fileInformation,
                                     GenTypeModel genTypeModel, OutputStream out) {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/" + templateLocation);

        JtwigModel model = JtwigModel.newModel()
                .with("package", genTypeModel.getPackageName())
                .with("className", genTypeModel.getClassName());
        model = addCustomInformation(model, fileInformation);

        ByteArrayOutputStream inMemOut = new ByteArrayOutputStream();
        PrintStream inMemPrint = new PrintStream(inMemOut);

        template.render(model, inMemPrint);

        String fileContent = new String(inMemOut.toByteArray(), StandardCharsets.UTF_8);
        String formattedContent = TemplateUtil.formatFile(fileContent);

        try {
            out.write(formattedContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation);

}
