package github.jdrost1818.plaster.service;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.domain.JavaDependency;
import github.jdrost1818.plaster.domain.JavaType;
import github.jdrost1818.plaster.exception.PlasterException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TypeServiceTest {

    @Mock
    ConfigurationService configurationService;

    @Mock
    SearchService searchService;

    @Mock
    DependencyService dependencyService;

    TypeService classUnderTest = new TypeService();

    @Before
    public void setUp() {
        initMocks(this);

        this.classUnderTest.setConfigurationService(configurationService);
        this.classUnderTest.setSearchService(searchService);
        this.classUnderTest.setDependencyService(dependencyService);
    }

    @Test(expected = PlasterException.class)
    public void convertToType_malformed_type() throws Exception {
        this.classUnderTest.convertToType("Map<<string>");
    }

    @Test
    public void convertToType_should_be_primitive() throws Exception {
        when(this.configurationService.getBoolean(Setting.SHOULD_USE_PRIMITIVES)).thenReturn(true);

        JavaType foundType = this.classUnderTest.convertToType("int");

        assertThat(foundType.getType().getDeclaration(), equalTo("int"));
    }

    @Test
    public void convertToType_should_not_be_primitive() throws Exception {
        when(this.configurationService.getBoolean(Setting.SHOULD_USE_PRIMITIVES)).thenReturn(false);

        JavaType foundType = this.classUnderTest.convertToType("int");

        assertThat(foundType.getType().getDeclaration(), equalTo("Integer"));
    }

    @Test(expected = PlasterException.class)
    public void convertToType_custom_type_too_many_results() throws Exception {
        String className = "CustomClass";
        List<String> expectedResult = Lists.newArrayList("1", "2");

        when(this.configurationService.getBoolean(Setting.SHOULD_USE_PRIMITIVES)).thenReturn(true);
        when(this.searchService.findClassesWithName(className)).thenReturn(expectedResult);

        this.classUnderTest.convertToType(className);
    }

    @Test(expected = PlasterException.class)
    public void convertToType_custom_type_no_results() throws Exception {
        String className = "CustomClass";
        List<String> expectedResult = Lists.newArrayList();

        when(this.configurationService.getBoolean(Setting.SHOULD_USE_PRIMITIVES)).thenReturn(true);
        when(this.searchService.findClassesWithName(className)).thenReturn(expectedResult);

        this.classUnderTest.convertToType(className);
    }

    @Test
    public void convertToType_custom_type() throws Exception {
        String className = "CustomClass";
        String filePath = "src/main/java/com/example/CustomClass.java";

        List<String> expectedSearchResult = Lists.newArrayList(filePath);
        List<JavaDependency> dependencies = Lists.newArrayList();

        when(this.configurationService.getBoolean(Setting.SHOULD_USE_PRIMITIVES)).thenReturn(true);
        when(this.searchService.findClassesWithName(className)).thenReturn(expectedSearchResult);
        when(this.dependencyService.fetchDependencies(className)).thenReturn(dependencies);

        JavaType javaType = this.classUnderTest.convertToType(className);

        assertThat(javaType.getType().getDeclaration(), equalTo(className));
        assertThat(javaType.getDependencies(), sameInstance(dependencies));
    }

    /*
        VALIDATOR
     */
    @Test
    public void validateType_one_class() throws Exception {
        assertTrue(this.classUnderTest.validateType("Map"));
    }

    @Test
    public void validateType_single_type_arg() throws Exception {
        assertTrue(this.classUnderTest.validateType("Map<String>"));
    }

    @Test
    public void validateType_multiple_type_args() throws Exception {
        assertTrue(this.classUnderTest.validateType("Map<String, Integer>"));
    }

    @Test
    public void validateType_too_many_less_thans() {
        assertFalse(this.classUnderTest.validateType("Map<<String, Integer>"));
    }

    @Test
    public void validateType_missing_less_than() {
        assertFalse(this.classUnderTest.validateType("MapString, Integer>"));
    }

    @Test
    public void validateType_too_many_greater_thans() {
        assertFalse(this.classUnderTest.validateType("Map<String, Integer>>"));
    }

    @Test
    public void validateType_missing_greater_than() {
        assertFalse(this.classUnderTest.validateType("Map<String, Integer"));
    }

    @Test
    public void validateType_wrongly_bracketed() {
        assertFalse(this.classUnderTest.validateType("Map<<String, Integer>>"));
    }

    @Test
    public void validateType_primitive_in_type() {
        assertFalse(this.classUnderTest.validateType("List<boolean>"));
    }


}