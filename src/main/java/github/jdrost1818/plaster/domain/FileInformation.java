package github.jdrost1818.plaster.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class FileInformation {

    private String className;

    private Field id;

    private List<Field> fields;

}
