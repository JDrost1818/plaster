package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.template.builder.ModelTemplateBuilder;
import github.jdrost1818.plaster.util.PathUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class GenerateService {

    @Setter
    private ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    public void generateModel(FileInformation fileInformation) {
        GenTypeModel genTypeModel = new GenTypeModel(
                this.getCustomPackage(Setting.REL_MODEL_PACKAGE),
                fileInformation.getClassName(),
                this.configurationService.getBoolean(Setting.IS_LOMBOK_ENABLED));

        String genFilePath = this.getRenderLocation(Setting.REL_MODEL_PACKAGE, fileInformation.getClassName());
        ModelTemplateBuilder.getInstance().renderTemplate(
                "model/model.twig",
                fileInformation,
                genTypeModel,
                this.getOutputStream(genFilePath));
    }

    public void generateController(FileInformation fileInformation) {
        
    }
    
    public void generateService(FileInformation fileInformation) {
        
    }
    
    public void generateRepository(FileInformation fileInformation) {
        
    }
    
    public void addFields(FileInformation fileInformation) {
        
    }

    private OutputStream getOutputStream(String location) {
        File file = new File(location);
        if (file.exists()) {
            throw new PlasterException("Cannot generate. Already exists: " + location);
        }
        try {
            if (file.getParentFile().mkdirs() && file.createNewFile()) {
                return new FileOutputStream(new File(location));
            } else {
                throw new PlasterException("Error creating file. Ensure you have permissions to perform this action");
            }
        } catch (IOException e) {
            throw new PlasterException("Error creating file. Ensure you have permissions to perform this action");
        }
    }

    private String getCustomPackage(Setting setting) {
        String appPackage = this.configurationService.get(Setting.APP_PATH);
        String relGenPackage = this.configurationService.get(setting);
        String customGenPackage = this.configurationService.get(Setting.SUB_DIR_PATH);

        String path = PathUtil.joinPath(appPackage, relGenPackage, customGenPackage);

        return PathUtil.pathToPackage(path);
    }

    private String getRenderLocation(Setting setting, String className) {
        String projectPath = this.configurationService.get(Setting.PROJECT_PATH);
        String basePath = this.configurationService.get(Setting.BASE_PATH);
        String appPath = this.configurationService.get(Setting.APP_PATH);
        String dirPath = this.configurationService.get(setting);
        String customPath = this.configurationService.get(Setting.SUB_DIR_PATH);
        String fileName = className + ".java";

        return PathUtil.joinPath(projectPath, basePath, appPath, dirPath, customPath, fileName);
    }
    
}
