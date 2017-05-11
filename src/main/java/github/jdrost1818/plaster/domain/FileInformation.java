package github.jdrost1818.plaster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FileInformation {

    private String className;

    private Field id;

    private List<Field> fields;

}
