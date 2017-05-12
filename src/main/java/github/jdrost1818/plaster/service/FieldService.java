package github.jdrost1818.plaster.service;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.domain.Field;
import github.jdrost1818.plaster.domain.TypeDeclaration;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.util.TypeUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class FieldService {

    @Setter
    private TypeService typeService = ServiceProvider.getTypeService();

    /**
     * Converts the list name:type strings to a list of {@link Field}
     *
     * @param fieldStrings
     *          strings to convert
     * @return the converted fields
     */
    public List<Field> convertToFields(List<String> fieldStrings) {
        if (CollectionUtils.isEmpty(fieldStrings)) {
            return Lists.newArrayList();
        }

        return fieldStrings.stream()
                .map(this::convertToField)
                .collect(Collectors.toList());
    }

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
        String name = TypeUtil.normalizeVariableName(parts[0]);
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
        if (isNull(fieldPair) || fieldPair.split(":").length != 2) {
            throw new PlasterException("Invalid field declaration found: " + fieldPair);
        }
    }

}
