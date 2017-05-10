package github.jdrost1818.plaster.domain;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

import static java.util.Objects.nonNull;

@Data
public class JavaType {

    private JavaTypeDeclaration type;

    private List<JavaDependency> dependencies;

    public JavaType(String type) {
        this(type, Lists.newArrayList());
    }

    public JavaType(String type, List<JavaDependency> dependencies) {
        this.type = new JavaTypeDeclaration(type);
        this.dependencies = dependencies;
    }

    public List<JavaDependency> getDependencies() {
        return nonNull(this.dependencies) ? this.dependencies : Lists.newArrayList();
    }

}
