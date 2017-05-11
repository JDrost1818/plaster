package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.domain.Field;
import github.jdrost1818.plaster.domain.TypeDeclaration;
import github.jdrost1818.plaster.exception.PlasterException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class FieldService {

    @Setter
    private TypeService typeService = ServiceProvider.getTypeService();

    /**
     * Converts the name:type string to a {@link Field}
     *
     * @param fieldString
     *          string to convert
     * @return the converted field
     */
    public Field convertToField(String fieldString) {
        validateFieldPair(fieldString);

        String[] parts = fieldString.split(":");
        String name = parts[0];
        String typeString = parts[1];

        TypeDeclaration typeDeclaration = this.typeService.convertToTypeDeclaration(typeString);

        return new Field(typeDeclaration, name);
    }

    /**
     * Validates that the name:type pair is valid
     *
     * @param fieldPair
     *          string to validate
     * @throws PlasterException if not valid
     */
    public static void validateFieldPair(String fieldPair) {
        if (fieldPair.split(":").length != 2) {
            throw new PlasterException("Invalid field declaration found: " + fieldPair);
        }
    }

}
