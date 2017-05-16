package github.jdrost1818.plaster.domain.template;

import github.jdrost1818.plaster.domain.Field;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlattenedField {

    private String packagePath;

    private String className;

    private String varName;

    public FlattenedField(Field field) {
        this.packagePath = "";
        this.className = field.getTypeDeclaration().getDeclaration();
        this.varName = field.getVariableName();
    }

}
