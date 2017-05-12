package github.jdrost1818.plaster.template;

import github.jdrost1818.plaster.domain.*;
import lombok.experimental.UtilityClass;
import org.jtwig.JtwigModel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public final class TemplateUtil {

    public static JtwigModel addDependencies(JtwigModel model, FileInformation fileInformation) {
        String dependencyString = fileInformation.getFields().stream()
                .map(Field::getTypeDeclaration)
                .map(TypeDeclaration::getTypes)
                .flatMap(List::stream)
                .map(Type::getDependency)
                .filter(Objects::nonNull)
                .map(Dependency::getTemplate)
                .distinct()
                .collect(Collectors.joining("\n"));

        return model.with("dependencies", dependencyString);
    }

    public static JtwigModel addId(JtwigModel model, FileInformation fileInformation) {
        return model.with("idField", fileInformation.getId().getTemplate());
    }

    public static JtwigModel addFields(JtwigModel model, FileInformation fileInformation) {
        String fieldString = fileInformation.getFields().stream()
                .map(Field::getTemplate)
                .collect(Collectors.joining("\n\n"));

        return model.with("fields", fieldString);
    }

    public static String formatFile(String file) {
        return file.replaceAll("(\r?\n){3,}", "\r\n\r\n");
    }

}
