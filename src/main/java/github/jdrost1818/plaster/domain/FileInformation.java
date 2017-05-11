package github.jdrost1818.plaster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FileInformation {

    private String className;

    private Field id;

    private List<Field> fields;

}
