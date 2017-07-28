package github.jdrost1818.plaster.service.modifier;

import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.type.ControllerModifier;
import github.jdrost1818.plaster.service.type.ModelModifier;
import github.jdrost1818.plaster.service.type.RepositoryModifier;
import github.jdrost1818.plaster.service.type.ServiceModifier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteService implements ModelModifier, ControllerModifier, ServiceModifier, RepositoryModifier {

    @Override
    public void modifyModel(FileInformation fileInformation) {
        System.out.println("Deleting Model");
    }

    @Override
    public void modifyService(FileInformation fileInformation) {
        System.out.println("Deleting Service");
    }

    @Override
    public void modifyController(FileInformation fileInformation) {
        System.out.println("Deleting Controller");
    }

    @Override
    public void modifyRepository(FileInformation fileInformation) {
        System.out.println("Deleting Repository");
    }

}
