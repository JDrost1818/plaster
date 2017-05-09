package github.jdrost1818.domain;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

import static java.util.Objects.nonNull;

@Data
public class JavaType {

    private JavaTypeDeclaration type;

    private List<String> dependencies;

    public JavaType(String type) {
        this(type, Lists.newArrayList());
    }

    public JavaType(String type, List<String> dependencies) {
        this.type = new JavaTypeDeclaration(type);
        this.dependencies = dependencies;
    }

    public List<String> getDependencies() {
        return nonNull(this.dependencies) ? this.dependencies : Lists.newArrayList();
    }

}
