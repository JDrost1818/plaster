package github.jdrost1818.plaster.service.modifier;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.template.*;
import github.jdrost1818.plaster.service.type.ControllerModifier;
import github.jdrost1818.plaster.service.type.ModelModifier;
import github.jdrost1818.plaster.service.type.RepositoryModifier;
import github.jdrost1818.plaster.service.type.ServiceModifier;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@AllArgsConstructor
public class GenerateService implements ModelModifier, ControllerModifier, ServiceModifier, RepositoryModifier {

    private final ConfigurationService configurationService;

    private final UtilityService utilityService;

    private final ModelTemplateService modelTemplateService;

    private final ModelTestTemplateService modelTestTemplateService;

    private final ControllerTemplateService controllerTemplateService;

    private final ControllerTestTemplateService controllerTestTemplateService;

    private final ServiceTemplateService serviceTemplateService;

    private final ServiceTestTemplateService serviceTestTemplateService;

    private final RepositoryTemplateService repositoryTemplateService;

    private final RepositoryTestTemplateService repositoryTestTemplateService;

    @Override
    public void modifyModel(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.MODEL, this.modelTemplateService);
        if (this.configurationService.getBoolean(Setting.IS_TESTING_ENABLED)) {
            this.generate(fileInformation, TemplateType.MODEL_TEST, this.modelTestTemplateService);
        }
    }

    @Override
    public void modifyController(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.CONTROLLER, this.controllerTemplateService);
        if (this.configurationService.getBoolean(Setting.IS_REST_DOCUMENTATION_TESTING_ENABLED)) {
            this.generate(fileInformation, TemplateType.CONTROLLER_TEST, this.controllerTestTemplateService);
        }
    }

    @Override
    public void modifyService(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.SERVICE, this.serviceTemplateService);
        if (this.configurationService.getBoolean(Setting.IS_TESTING_ENABLED)) {
            this.generate(fileInformation, TemplateType.SERVICE_TEST, this.serviceTestTemplateService);
        }
    }

    @Override
    public void modifyRepository(FileInformation fileInformation) {
        this.generate(fileInformation, TemplateType.REPOSITORY, this.repositoryTemplateService);
        if (this.configurationService.getBoolean(Setting.IS_TESTING_ENABLED)) {
            this.generate(fileInformation, TemplateType.REPOSITORY_TEST, this.repositoryTestTemplateService);
        }
    }

    private void generate(FileInformation fileInformation, TemplateType templateType, TemplateService templateService) {
        String genFilePath = this.utilityService.getFilePath(fileInformation, templateType);
        String renderedFileString = templateService.renderTemplate(fileInformation);

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
    
}
