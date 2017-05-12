package github.jdrost1818.plaster.service.it;

import github.jdrost1818.plaster.domain.Dependency;
import github.jdrost1818.plaster.domain.Type;
import github.jdrost1818.plaster.domain.TypeDeclaration;
import github.jdrost1818.plaster.service.ConfigurationServiceTest;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.TypeService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class TypeServiceTestIt {

    private TypeService classUnderTest = ServiceProvider.getTypeService();

    @Before
    public void setUp() {
        ConfigurationServiceTest.transformToItService(ServiceProvider.getConfigurationService());

        this.classUnderTest.setSearchService(ServiceProvider.getSearchService());
        this.classUnderTest.setDependencyService(ServiceProvider.getDependencyService());
    }

    @Test
    public void convertToType_stored() {
        Type foundType = this.classUnderTest.convertToType("List", true);

        assertThat(foundType.getClassName(), equalTo("List"));
        assertThat(foundType.getDependency().getPath(), equalTo("java.util.List"));
    }

    @Test
    public void convertToType() {
        Type foundType = this.classUnderTest.convertToType("Example", false);

        assertThat(foundType.getClassName(), equalTo("Example"));
        assertThat(foundType.getDependency().getPath(), equalTo("com.example.app.dir1.Example"));
    }

    @Test
    public void convertToTypeDeclaration() {
        TypeDeclaration foundTypeDeclaration = this.classUnderTest.convertToTypeDeclaration("Map< List < String >,List<Example>>");

        assertThat(foundTypeDeclaration.getDeclaration(), equalTo("Map<List<String>, List<Example>>"));

        List<Type> types = foundTypeDeclaration.getTypes();
        assertThat(types, hasSize(5));
        assertThat(types, hasItems(
                new Type("Map", new Dependency("java.util.Map")),
                new Type("List", new Dependency("java.util.List")),
                new Type("String", null),
                new Type("List", new Dependency("java.util.List")),
                new Type("Example", new Dependency("com.example.app.dir1.Example"))
        ));
    }
}
