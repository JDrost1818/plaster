package github.jdrost1818.plaster.domain;

import lombok.Getter;

@Getter
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
        return null;
    }
}
