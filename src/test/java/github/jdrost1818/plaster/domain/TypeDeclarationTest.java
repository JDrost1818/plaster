package github.jdrost1818.plaster.domain;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TypeDeclarationTest {

    @Test
    public void getAllTypeDeclarations_single_declaration() throws Exception {
        String type = "int";

        TypeDeclaration typeDeclaration = new TypeDeclaration(type);

        List<TypeDeclaration> typeDeclarations = typeDeclaration.getAllTypeDeclarations();

        assertThat(typeDeclarations, hasSize(1));
        assertThat(typeDeclarations.get(0).getDeclaration(), equalTo(type));
    }

    @Test
    public void getAllTypeDeclarations_complex_declaration() throws Exception {
        String type1 = "Map";
        String type2 = "List";
        String type3 = "Integer";
        String type4 = "List";
        String type5 = "Integer";

        String allTypes = String.format("%s<%s<%s>, %s<%s>>", type1, type2, type3, type4, type5);

        TypeDeclaration typeDeclaration = new TypeDeclaration(allTypes);

        List<TypeDeclaration> typeDeclarations = typeDeclaration.getAllTypeDeclarations();

        // Have to filter out duplicates
        assertThat(typeDeclarations, hasSize(3));

        List<String> typeStrings = typeDeclarations.stream()
                .map(TypeDeclaration::getDeclaration)
                .collect(Collectors.toList());

        assertThat(typeStrings, contains(type1, type2, type3));
    }

    @Test
    public void getTemplate() throws Exception {
        String templateString = "List<Integer>";

        TypeDeclaration declaration = new TypeDeclaration(templateString);
        assertThat(declaration.getTemplate(), equalTo(templateString));
    }

}