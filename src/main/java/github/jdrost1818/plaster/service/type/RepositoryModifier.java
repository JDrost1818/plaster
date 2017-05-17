package github.jdrost1818.plaster.service.type;

import github.jdrost1818.plaster.domain.FileInformation;

public interface RepositoryModifier {

    void modifyRepository(FileInformation fileInformation);

}
