package github.jdrost1818.plaster.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class GenTypeModel {

    private String packageName;

    private String className;

    private boolean lombokEnabled;

    public GenTypeModel(@NonNull String packageName, @NonNull String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public GenTypeModel(@NonNull String packageName, @NonNull String className, boolean lombokEnabled) {
        this.packageName = packageName;
        this.className = className;
        this.lombokEnabled = lombokEnabled;
    }

}
