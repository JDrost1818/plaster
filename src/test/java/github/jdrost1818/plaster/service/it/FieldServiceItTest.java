package github.jdrost1818.plaster.service.it;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.StoredJavaType;
import github.jdrost1818.plaster.domain.Dependency;
import github.jdrost1818.plaster.domain.Field;
import github.jdrost1818.plaster.domain.Type;
import github.jdrost1818.plaster.domain.TypeDeclaration;
import github.jdrost1818.plaster.service.ConfigurationServiceTest;
import github.jdrost1818.plaster.service.FieldService;
import github.jdrost1818.plaster.service.ServiceProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FieldServiceItTest {

    private FieldService classUnderTest = ServiceProvider.getFieldService();

    @Before
    public void setUp() {
        ConfigurationServiceTest.transformToItService(ServiceProvider.getConfigurationService());

        this.classUnderTest.setTypeService(ServiceProvider.getTypeService());
    }

    @Test
    public void convertToFields() {
        List<String> fieldPairs = Lists.newArrayList(
                "something:date",
                "some_other_thing:list<int>",
                "weird other thing: map<int, example>");

        List<Field> expected = Lists.newArrayList(
                new Field(new TypeDeclaration("Date",
                        Lists.newArrayList(
                                StoredJavaType.DATE.getType(false))),
                        "something"),
                new Field(new TypeDeclaration("List<Integer>",
                        Lists.newArrayList(
                                StoredJavaType.LIST.getType(false),
                                StoredJavaType.INTEGER.getType(false))),
                        "someOtherThing"),
            new Field(new TypeDeclaration("Map<Integer, Example>",
                Lists.newArrayList(
                        StoredJavaType.MAP.getType(false),
                        StoredJavaType.INTEGER.getType(false),
                        new Type("Example", new Dependency("com.example.app.dir1.Example")))),
                "weirdOtherThing")
        );

        List<Field> result = this.classUnderTest.convertToFields(fieldPairs);

        assertThat(result, equalTo(expected));
    }
}
