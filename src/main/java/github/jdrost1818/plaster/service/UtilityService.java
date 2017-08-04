package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.template.Template;
import github.jdrost1818.plaster.util.PathUtil;
import lombok.AllArgsConstructor;

import java.nio.file.Paths;

@AllArgsConstructor
public class UtilityService {

    private final ConfigurationService configurationService;

    public String getFilePath(FileInformation fileInformation, ModeScope scope) {
        TemplateType templateType = TemplateType.valueOf(scope.name());

        return getFilePath(fileInformation, templateType);
    }

    public String getFilePath(FileInformation fileInformation, TemplateType templateType) {
        String projectPath = this.configurationService.get(Setting.PROJECT_PATH);
        String basePath = this.configurationService.get(Setting.BASE_PATH);
        String appPath = this.configurationService.get(Setting.APP_PATH);
        String dirPath = this.configurationService.get(templateType.relPathSetting);
        String customPath = this.configurationService.get(Setting.SUB_DIR_PATH);
        String fileName = fileInformation.getClassName() + templateType.suffix + ".java";

        return PathUtil.joinPath(projectPath, basePath, appPath, dirPath, customPath, fileName);
    }

    public boolean fileExists(FileInformation fileInformation, ModeScope scope) {
        return Paths.get(getFilePath(fileInformation, scope)).toFile().exists();
    }

    public boolean fileExists(FileInformation fileInformation, TemplateType templateType) {
        return Paths.get(getFilePath(fileInformation, templateType)).toFile().exists();
    }

}