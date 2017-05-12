package github.jdrost1818.plaster.domain.template;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlattenedField {

    private String packagePath;

    private String className;

    private String varName;

}
