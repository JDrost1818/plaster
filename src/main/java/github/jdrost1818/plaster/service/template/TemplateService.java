package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.*;
import github.jdrost1818.plaster.domain.template.FlattenedField;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.util.PathUtil;
import github.jdrost1818.plaster.util.TypeUtil;
import lombok.AllArgsConstructor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class TemplateService {

    private final ConfigurationService configurationService;

    public abstract JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel);

    public final void renderTemplate(String templateLocation, FileInformation fileInformation,
                                     GenTypeModel genTypeModel, OutputStream out) {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/" + templateLocation);

        JtwigModel model = JtwigModel.newModel();
        model = addCustomInformation(model, fileInformation, genTypeModel);

        ByteArrayOutputStream inMemOut = new ByteArrayOutputStream();
        PrintStream inMemPrint = new PrintStream(inMemOut);

        template.render(model, inMemPrint);

        String fileContent = new String(inMemOut.toByteArray(), StandardCharsets.UTF_8);
        String formattedContent = formatFile(fileContent);

        try {
            out.write(formattedContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    JtwigModel addDependencies(JtwigModel model, FileInformation fileInformation) {
        List<Field> fields = new ArrayList<>(fileInformation.getFields());
        fields.add(fileInformation.getId());

        List<Dependency> dependencies = fields.stream()
                .map(Field::getTypeDeclaration)
                .map(TypeDeclaration::getTypes)
                .flatMap(List::stream)
                .map(Type::getDependency)
                .collect(Collectors.toList());

        return addDependencies(model, dependencies);
    }

    JtwigModel addDependencies(JtwigModel model, Field field) {
        List<Dependency> dependencies = field.getTypeDeclaration().getTypes().stream()
                .map(Type::getDependency)
                .collect(Collectors.toList());

        return addDependencies(model, dependencies);
    }

    JtwigModel addDependencies(JtwigModel model, List<Dependency> dependencies) {
         String dependencyString = dependencies.stream()
                .filter(Objects::nonNull)
                .map(Dependency::getTemplate)
                .distinct()
                .collect(Collectors.joining("\n"));

        return model.with("dependencies", dependencyString);
    }

    /**
     * Adds the id field attached to the {@link FileInformation} as a {@link FlattenedField}
     * to the provided model.
     *
     * @param model
     *          model to which to add the id field
     * @param fileInformation
     *          information about the file to generate
     * @return the modified model
     */
    JtwigModel addId(JtwigModel model, FileInformation fileInformation) {
        return model.with("idField", new FlattenedField(fileInformation.getId()));
    }

    /**
     * Adds the fields attached to the {@link FileInformation} as {@link FlattenedField}s
     * to the provided model.
     *
     * @param model
     *          model to which to add the fields
     * @param fileInformation
     *          information about the file to generate
     * @return the modified model
     */
    JtwigModel addFields(JtwigModel model, FileInformation fileInformation) {
        List<FlattenedField> fields = fileInformation.getFields().stream()
                .map(FlattenedField::new)
                .collect(Collectors.toList());

        return model.with("fields", fields);
    }

    /**
     * Adds fields in a consistent manner using a {@link FlattenedField}
     *
     * @param model
     *          model to which to add the field
     * @param genTypeModel
     *          model store
     * @param templateType
     *          which type are we adding
     * @return the modified model
     */
    JtwigModel addTypeField(JtwigModel model, GenTypeModel genTypeModel, TemplateType templateType) {
        String packageName = getCustomPackage(templateType.relPathSetting);
        String className = TypeUtil.normalizeTypeString(genTypeModel.getClassName() + templateType.suffix);
        String varName = TypeUtil.normalizeVariableName(genTypeModel.getClassName()) + templateType.suffix;

        return model.with(templateType.templateVarName, new FlattenedField(packageName, className, varName));
    }

    /**
     * Formats a string using the best Java formatting standards to ensure the generated
     * file is of proper format.
     *
     * @param fileString
     *          content to format
     * @return the formatted file content
     */
    String formatFile(String fileString) {
        return fileString.replaceAll("(\r?\n){3,}", "\r\n\r\n");
    }

    /**
     * Gets the full path in which to generate a file for a given setting.
     *
     * Concatenates the {@link Setting#APP_PATH}, the giving setting path,
     * and the {@link Setting#SUB_DIR_PATH} and joins them in a safe and consistent way.
     *
     * @param setting
     *          setting for which to get a path
     * @return the path to generate the given path
     */
    private String getCustomPackage(Setting setting) {
        String appPackage = configurationService.get(Setting.APP_PATH);
        String relGenPackage = configurationService.get(setting);
        String customGenPackage = configurationService.get(Setting.SUB_DIR_PATH);

        String path = PathUtil.joinPath(appPackage, relGenPackage, customGenPackage);

        return PathUtil.pathToPackage(path);
    }

}
