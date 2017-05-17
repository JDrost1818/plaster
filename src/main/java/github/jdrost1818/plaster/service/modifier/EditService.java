package github.jdrost1818.plaster.service.modifier;

import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.type.FieldModifier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditService implements FieldModifier {

    @Override
    public void modifyField(FileInformation fileInformation) {
        System.out.println("HERE");
    }

}
