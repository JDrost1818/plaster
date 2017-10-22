package github.jdrost1818.plaster.service.task.delete;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.task.PlasterTask;
import github.jdrost1818.plaster.service.task.PlasterTaskId;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

abstract class DeleteTask extends PlasterTask {

    @Setter
    private static UtilityService utilityService = ServiceProvider.getUtilityService();

    final PlasterTask nextGeneration;

    final TemplateType templateType;

    DeleteTask(String errorMsg, PlasterTaskId taskId, TemplateType templateType, PlasterTask nextGeneration) {
        super(errorMsg, taskId);

        this.templateType = templateType;
        this.nextGeneration = nextGeneration;
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        if (utilityService.fileExists(fileInformation, this.templateType)) {
            this.delete(fileInformation, this.templateType);
        } else {
            String infoMessage = String.format(
                    "%s could not be found for scope ['%s'] - skipping deletion",
                    fileInformation.getClassName(),
                    this.taskId.name());

            System.out.println(infoMessage);
        }

        return true;
    }

    @Override
    protected PlasterTask success() {
        return this.nextGeneration;
    }

    private void delete(FileInformation fileInformation, TemplateType templateType) {
        String filePath = utilityService.getFilePath(fileInformation, templateType);

        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new PlasterException("Error while deleting " + fileInformation + templateType.suffix);
        }
    }

}
