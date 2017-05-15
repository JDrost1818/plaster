package github.jdrost1818.plaster.service.template;

import com.google.common.collect.Lists;
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
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class TemplateService {

    private final ConfigurationService configurationService;

    /**
     * Adds information not applicable to all types of rendering being done.
     *
     * @param model
     *          model to which to add information
     * @param fileInformation
     *          information about the file to generate
     * @param genTypeModel
     *          model store
     * @return the fully-customized model object
     */
    abstract JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel);

    /**
     * Gets the appropriate template with which to perform the rendering
     *
     * @return the Jtwig template
     */
    abstract JtwigTemplate getTemplate();

    /**
     * The entry point to render a template.
     * @param fileInformation
     * @param genTypeModel
     * @return
     */
    public final String renderTemplate(FileInformation fileInformation, GenTypeModel genTypeModel) {
        JtwigTemplate template = getTemplate();

        JtwigModel model = JtwigModel.newModel();
        model = addCustomInformation(model, fileInformation, genTypeModel);

        ByteArrayOutputStream inMemOut = new ByteArrayOutputStream();
        PrintStream inMemPrint = new PrintStream(inMemOut);

        template.render(model, inMemPrint);

        String fileContent = new String(inMemOut.toByteArray(), StandardCharsets.UTF_8);

        return formatFile(fileContent);
    }

    /**
     * Adds the unique {@link Dependency}s for the fields attached to
     * the file information provided
     *
     * @param model
     *          model to which to add the dependencies
     * @param fileInformation
     *          information about the file to generate
     * @return the modified model
     */
    JtwigModel addDependencies(JtwigModel model, FileInformation fileInformation) {
        List<Field> fields = new ArrayList<>(fileInformation.getFields());
        fields.add(fileInformation.getId());

        return addDependencies(model, fields);
    }

    /**
     * Adds the {@link Dependency} for the given field to the model provided
     *
     * @param model
     *          model to which to add the dependency
     * @param field
     *          field which contains the dependency to add
     * @return the modified model
     */
    JtwigModel addDependencies(JtwigModel model, Field field) {
        return addDependencies(model, Lists.newArrayList(field));
    }

    /**
     * Adds the unique {@link Dependency}s for the given fields to the model provided
     *
     * @param model
     *          model to which to add the dependencies
     * @param fields
     *          fields which contain the dependencies to add
     * @return the modified model
     */
    JtwigModel addDependencies(JtwigModel model, List<Field> fields) {
         List<Dependency> dependencies = fields.stream()
                 .map(Field::getTypeDeclaration)
                 .map(TypeDeclaration::getTypes)
                 .flatMap(List::stream)
                 .map(Type::getDependency)
                 .filter(Objects::nonNull)
                 .distinct()
                 .collect(Collectors.toList());

        return model.with("dependencies", dependencies);
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
        return fileString.replaceAll("(\r?\n){3,}", "\n\n").replaceAll("\t", "    ");
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
