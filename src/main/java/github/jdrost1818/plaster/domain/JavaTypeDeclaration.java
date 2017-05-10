package github.jdrost1818.plaster.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This represents the type declaration when defining a variable.
 * For example:
 *
 *  private int var; ->                             JavaTypeDeclaration represents "int"
 *  private Map<List<Integer>, SomeClass> map; ->   JavaTypeDeclaration represents "Map<List<Integer>, SomeClass>"
 */
@Getter
public class JavaTypeDeclaration implements PlasterTemplate {

    private final String declaration;

    public JavaTypeDeclaration(String declaration) {
        this.declaration = declaration;
    }

    /**
     * This will get all the unique subclasses in the declaration.
     * For example:
     *
     *  private int var; ->                                     ["int"]
     *  private Map<List<Integer>, List<SomeClass>> map; ->     ["Map", "List", "Integer", "SomeClass"]
     *
     * @return the list of classes contained by the declaration
     */
    public List<JavaTypeDeclaration> getAllTypeDeclarations() {
        // Map<String, List<Something>> -> Map String List Something
        String trimmedDeclaration = this.declaration
                .replaceAll("[<>,]", " ")
                .replaceAll(" +", " ")
                .trim();

        return Arrays.stream(trimmedDeclaration.split(" "))
                .distinct()
                .map(JavaTypeDeclaration::new)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTemplate() {
        return this.declaration;
    }

}
