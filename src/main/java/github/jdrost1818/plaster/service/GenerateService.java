package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.template.TemplateBuilder;
import github.jdrost1818.plaster.template.builder.ControllerTemplateBuilder;
import github.jdrost1818.plaster.template.builder.ModelTemplateBuilder;
import github.jdrost1818.plaster.template.builder.RepositoryTemplateBuilder;
import github.jdrost1818.plaster.template.builder.ServiceTemplateBuilder;
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
        this.generate(fileInformation, TemplateType.MODEL, ModelTemplateBuilder.getInstance());
    }

    public void generateController(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.CONTROLLER, ControllerTemplateBuilder.getInstance());
    }
    
    public void generateService(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.SERVICE, ServiceTemplateBuilder.getInstance());
    }
    
    public void generateRepository(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.REPOSITORY, RepositoryTemplateBuilder.getInstance());
    }
    
    public void addFields(FileInformation fileInformation) {
        
    }

    private void generate(FileInformation fileInformation, TemplateType templateType, TemplateBuilder templateBuilder) {
        GenTypeModel genTypeModel = new GenTypeModel(
                fileInformation.getClassName(),
                this.configurationService.getBoolean(Setting.IS_LOMBOK_ENABLED));

        String genFilePath = this.getRenderLocation(templateType.relPathSetting, fileInformation.getClassName() + templateType.suffix);
        templateBuilder.renderTemplate(
                templateType.templateLocation,
                fileInformation,
                genTypeModel,
                this.getOutputStream(genFilePath));
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
