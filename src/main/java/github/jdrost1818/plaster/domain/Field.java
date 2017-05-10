package github.jdrost1818.plaster.domain;

import lombok.Data;

@Data
public class Field implements PlasterTemplate {

    private TypeDeclaration typeDeclaration;

    private String variableName;

    @Override
    public String getTemplate() {
        return String.format("private %s %s;", this.typeDeclaration.getTemplate(), this.variableName);
    }
}
