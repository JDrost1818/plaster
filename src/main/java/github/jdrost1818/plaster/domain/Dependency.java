package github.jdrost1818.plaster.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Dependency implements PlasterTemplate {

    public String path;

    public Dependency(String path) {
        this.path = path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTemplate() {
        return String.format("import %s;", this.path);
    }
}
