package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.GenerationLocation;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.util.PathUtil;
import github.jdrost1818.plaster.util.TypeUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Paths;

@AllArgsConstructor
public class UtilityService {

    private final ConfigurationService configurationService;

    public String getFilePath(FileInformation fileInformation, TemplateType templateType) {
        String projectPath = this.configurationService.get(Setting.PROJECT_PATH);
        String basePath = this.configurationService.get(Setting.BASE_PATH);
        String appPath = this.configurationService.get(Setting.APP_PATH);
        String dirPath = this.configurationService.get(templateType.relPathSetting);
        String customPath = this.configurationService.get(Setting.SUB_DIR_PATH);
        String fileName = fileInformation.getClassName() + templateType.suffix + ".java";

        // Todo - This is going to get out of hand. Fix it... Somehow
        if (templateType.generationLocation == GenerationLocation.TEST) {
            basePath = basePath.replace("src/main", "src/test");
        } else if (templateType.generationLocation == GenerationLocation.ROOT_TEST) {
            basePath = basePath.replace("src/main", "src/test");
            String itFileName =  this.configurationService.get(Setting.APP_NAME)  + templateType.suffix;
            itFileName = TypeUtil.normalizeTypeString(itFileName) + ".java";

            return PathUtil.joinPath(projectPath, basePath, appPath, itFileName);
        }

        return PathUtil.joinPath(projectPath, basePath, appPath, dirPath, customPath, fileName);
    }

    public boolean fileExists(FileInformation fileInformation, TemplateType templateType) {
        return Paths.get(getFilePath(fileInformation, templateType)).toFile().exists();
    }

}
