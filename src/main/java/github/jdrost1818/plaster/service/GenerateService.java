package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.template.*;
import github.jdrost1818.plaster.util.PathUtil;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@AllArgsConstructor
public class GenerateService {

    private final ConfigurationService configurationService;

    private final ModelTemplateService modelTemplateService;

    private final ControllerTemplateService controllerTemplateService;

    private final ServiceTemplateService serviceTemplateService;

    private final RepositoryTemplateService repositoryTemplateService;

    public void generateModel(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.MODEL, this.modelTemplateService);
    }

    public void generateController(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.CONTROLLER, this.controllerTemplateService);
    }
    
    public void generateService(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.SERVICE, this.serviceTemplateService);
    }
    
    public void generateRepository(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.REPOSITORY, this.repositoryTemplateService);
    }
    
    public void addFields(FileInformation fileInformation) {
        throw new UnsupportedOperationException("Adding fields to already existing fields is not supported. Sorry.");
    }

    private void generate(FileInformation fileInformation, TemplateType templateType, TemplateService templateService) {
        GenTypeModel genTypeModel = new GenTypeModel(
                fileInformation.getClassName(),
                this.configurationService.getBoolean(Setting.IS_LOMBOK_ENABLED));

        String genFilePath = this.getRenderLocation(templateType.relPathSetting, fileInformation.getClassName() + templateType.suffix);
        String renderedFileString = templateService.renderTemplate(
                fileInformation,
                genTypeModel);

        try {
            this.getOutputStream(genFilePath).write(renderedFileString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OutputStream getOutputStream(String location) {
        File file = new File(location);
        if (file.exists()) {
            throw new PlasterException("Cannot generate. Already exists: " + location);
        }
        try {
            File dir = file.getParentFile();
            if ((dir.exists() || dir.mkdirs()) && file.createNewFile()) {
                return new FileOutputStream(new File(location));
            } else {
                throw new PlasterException("Error creating file. Ensure you have permissions to perform this action: " + location);
            }
        } catch (IOException e) {
            throw new PlasterException("Error creating file. Ensure you have permissions to perform this action: " + location);
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
