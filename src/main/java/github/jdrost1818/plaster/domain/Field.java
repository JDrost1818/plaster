package github.jdrost1818.plaster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field implements PlasterTemplate {

    private TypeDeclaration typeDeclaration;

    private String variableName;

    @Override
    public String getTemplate() {
        return String.format("\tprivate %s %s;", this.typeDeclaration.getTemplate(), this.variableName);
    }

}
