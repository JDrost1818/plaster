package github.jdrost1818.plaster.domain;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TypeDeclarationTest {


    @Test
    public void getTemplate() throws Exception {
        String templateString = "List<Integer>";

        TypeDeclaration declaration = new TypeDeclaration(templateString, Lists.newArrayList());
        assertThat(declaration.getTemplate(), equalTo(templateString));
    }

    @Test
    public void constructor_normalizes_name() throws Exception {
        String templateString = "Map<List<    Integer    >,Integer>";

        TypeDeclaration declaration = new TypeDeclaration(templateString, Lists.newArrayList());

        assertThat(declaration.getDeclaration(), equalTo("Map<List<Integer>, Integer>"));
    }

}