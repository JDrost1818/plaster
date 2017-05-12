package github.jdrost1818.plaster.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class GenTypeModel {

    private String className;

    private boolean lombokEnabled;

    public GenTypeModel(@NonNull String className) {
        this.className = className;
    }

    public GenTypeModel(@NonNull String className, boolean lombokEnabled) {
        this.className = className;
        this.lombokEnabled = lombokEnabled;
    }

}
