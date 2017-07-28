package github.jdrost1818.plaster.service.modifier;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.type.ControllerModifier;
import github.jdrost1818.plaster.service.type.ModelModifier;
import github.jdrost1818.plaster.service.type.RepositoryModifier;
import github.jdrost1818.plaster.service.type.ServiceModifier;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
public class DeleteService implements ModelModifier, ControllerModifier, ServiceModifier, RepositoryModifier {

    @Setter
    private final UtilityService utilityService;

    @Override
    public void modifyModel(FileInformation fileInformation) {
        this.delete(fileInformation, TemplateType.MODEL);
    }

    @Override
    public void modifyService(FileInformation fileInformation) {
        this.delete(fileInformation, TemplateType.SERVICE);
    }

    @Override
    public void modifyController(FileInformation fileInformation) {
        this.delete(fileInformation, TemplateType.CONTROLLER);
    }

    @Override
    public void modifyRepository(FileInformation fileInformation) {
        this.delete(fileInformation, TemplateType.REPOSITORY);
    }

    private void delete(FileInformation fileInformation, TemplateType templateType) {
        String filePath = this.utilityService.getFilePath(fileInformation, templateType);

        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new PlasterException("Error while deleting " + fileInformation + templateType.suffix);
        }
    }

}
