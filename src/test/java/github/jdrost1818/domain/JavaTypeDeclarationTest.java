package github.jdrost1818.domain;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class JavaTypeDeclarationTest {

    @Test
    public void getAllTypeDeclarations_single_declaration() throws Exception {
        String type = "int";

        JavaTypeDeclaration typeDeclaration = new JavaTypeDeclaration(type);

        List<JavaTypeDeclaration> typeDeclarations = typeDeclaration.getAllTypeDeclarations();

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

        JavaTypeDeclaration typeDeclaration = new JavaTypeDeclaration(allTypes);

        List<JavaTypeDeclaration> typeDeclarations = typeDeclaration.getAllTypeDeclarations();

        // Have to filter out duplicates
        assertThat(typeDeclarations, hasSize(3));

        List<String> typeStrings = typeDeclarations.stream()
                .map(JavaTypeDeclaration::getDeclaration)
                .collect(Collectors.toList());

        assertThat(typeStrings, contains(type1, type2, type3));
    }

    @Test
    public void getTemplate() throws Exception {
        String templateString = "List<Integer>";

        JavaTypeDeclaration declaration = new JavaTypeDeclaration(templateString);
        assertThat(declaration.getTemplate(), equalTo(templateString));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidType_space() throws Exception {
        new JavaTypeDeclaration("This IsNotValid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidType_number() throws Exception {
        new JavaTypeDeclaration("ThisIsNotValid9");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidType_lowercaseCamel() throws Exception {
        // Only primitives are allowed to start lowercase and
        // none of them have a uppercase letter later
        new JavaTypeDeclaration("thisIsNotValid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidType_primitive_type() throws Exception {
        new JavaTypeDeclaration("Map<int, String>");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidType_missing_brace_1() throws Exception {
        new JavaTypeDeclaration("Map<Integer, String");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidType_missing_brace_2() throws Exception {
        new JavaTypeDeclaration("List<Integer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidType_not_primitive_lowercase() throws Exception {
        new JavaTypeDeclaration("lowercase");
    }

    @Test
    public void validType() throws Exception {
        new JavaTypeDeclaration("Map");
    }

    @Test
    public void validTypea() throws Exception {
        new JavaTypeDeclaration("Integer");
    }

    @Test
    public void validType_complex() throws Exception {
        new JavaTypeDeclaration("Map<Map<String, List<Integer>>, List<SomethingElse>>");
    }

}