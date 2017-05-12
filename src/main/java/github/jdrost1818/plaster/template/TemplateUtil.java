package github.jdrost1818.plaster.template;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.*;
import github.jdrost1818.plaster.domain.template.FlattenedField;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.util.PathUtil;
import github.jdrost1818.plaster.util.TypeUtil;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.jtwig.JtwigModel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public final class TemplateUtil {

    @Setter
    private static ConfigurationService configurationService = ServiceProvider.getConfigurationService();

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

    public static JtwigModel addTypeField(JtwigModel model, GenTypeModel genTypeModel, TemplateType templateType) {
        String packageName = getCustomPackage(templateType.relPathSetting);
        String className = genTypeModel.getClassName() + templateType.suffix;
        String varName = TypeUtil.normalizeVariableName(genTypeModel.getClassName()) + templateType.suffix;

        return model.with(templateType.templateVarName, new FlattenedField(packageName, className, varName));
    }

    public static String formatFile(String file) {
        return file.replaceAll("(\r?\n){3,}", "\r\n\r\n");
    }

    private String getCustomPackage(Setting setting) {
        String appPackage = configurationService.get(Setting.APP_PATH);
        String relGenPackage = configurationService.get(setting);
        String customGenPackage = configurationService.get(Setting.SUB_DIR_PATH);

        String path = PathUtil.joinPath(appPackage, relGenPackage, customGenPackage);

        return PathUtil.pathToPackage(path);
    }

}
