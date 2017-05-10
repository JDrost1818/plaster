package github.jdrost1818.plaster.domain;

import github.jdrost1818.plaster.util.TypeUtil;
import lombok.Getter;

import java.util.List;

/**
 * This represents the type declaration when defining a variable.
 * For example:
 *
 *  private int var; ->                             TypeDeclaration represents "int"
 *  private Map<List<Integer>, SomeClass> map; ->   TypeDeclaration represents "Map<List<Integer>, SomeClass>"
 */
@Getter
public class TypeDeclaration implements PlasterTemplate {

    private final String declaration;

    private final List<Type> types;

    public TypeDeclaration(String declaration, List<Type> types) {
        this.declaration = TypeUtil.normalizeTypeString(declaration);
        this.types = types;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTemplate() {
        return this.declaration;
    }

}
