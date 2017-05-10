package github.jdrost1818.plaster.domain;

import lombok.Getter;

@Getter
public class JavaDependency implements PlasterTemplate {

    public String path;

    public JavaDependency(String path) {
        this.path = path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTemplate() {
        return null;
    }
}
