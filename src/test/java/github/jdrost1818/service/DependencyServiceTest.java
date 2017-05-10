package github.jdrost1818.service;

import com.google.common.collect.Lists;
import github.jdrost1818.data.Setting;
import github.jdrost1818.domain.JavaDependency;
import github.jdrost1818.domain.JavaType;
import github.jdrost1818.exception.PlasterException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DependencyServiceTest {

    @Mock
    ConfigurationService configurationService;

    @Mock
    SearchService searchService;

    DependencyService classUnderTest = ServiceProvider.getDependencyService();

    @Before
    public void setUp() {
        initMocks(this);

        this.classUnderTest.setConfigurationService(this.configurationService);
        this.classUnderTest.setSearchService(this.searchService);
    }

    @Test
    public void convertSystemPathToJavaPath_null() throws Exception {
        assertThat(this.classUnderTest.convertSystemPathToJavaPath(null), equalTo(null));
    }

    @Test
    public void convertSystemPathToJavaPath_empty() throws Exception {
        assertThat(this.classUnderTest.convertSystemPathToJavaPath(""), equalTo(""));

    }

    @Test
    public void convertSystemPathToJavaPath_linux() throws Exception {
        String absolutePath = "somewhere/project/module/src/main/java/com/example/somewhere/Something.java";

        when(this.configurationService.get(Setting.BASE_PATH)).thenReturn("src/main/java");
        String convertedPath = this.classUnderTest.convertSystemPathToJavaPath(absolutePath);

        assertThat(convertedPath, equalTo("com.example.somewhere.Something"));
    }

    @Test
    public void convertSystemPathToJavaPath_windows() throws Exception {
        String absolutePath = "somewhere\\project\\module\\src\\main\\java\\com\\example\\somewhere\\Something.java";

        when(this.configurationService.get(Setting.BASE_PATH)).thenReturn("src/main/java");
        String convertedPath = this.classUnderTest.convertSystemPathToJavaPath(absolutePath);

        assertThat(convertedPath, equalTo("com.example.somewhere.Something"));
    }

    @Test(expected = PlasterException.class)
    public void convertSystemPathToJavaPath_root_not_contained() throws Exception {
        String absolutePath = "somewhere/project/module/src/main/java/com/example/somewhere/Something.java";

        when(this.configurationService.get(Setting.BASE_PATH)).thenReturn("false/root");
        this.classUnderTest.convertSystemPathToJavaPath(absolutePath);
    }

    @Test
    public void fetchCustomDependencies_null() throws Exception {
        assertThat(this.classUnderTest.fetchDependencies(null), hasSize(0));
    }

    @Test
    public void fetchCustomDependencies_empty() throws Exception {
        assertThat(this.classUnderTest.fetchDependencies(""), hasSize(0));
    }

    @Test
    public void fetchCustomDependencies_stored_no_deps() throws Exception {
        assertThat(this.classUnderTest.fetchDependencies("int"), hasSize(0));
    }

    @Test
    public void fetchCustomDependencies_stored() throws Exception {
        assertThat(this.classUnderTest.fetchDependencies("List<Integer>"), hasSize(1));
    }

    @Test
    public void fetchCustomDependencies_stored_remove_duplicates() throws Exception {
        assertThat(this.classUnderTest.fetchDependencies("List<List<Integer>>"), hasSize(1));
    }

    @Test(expected = PlasterException.class)
    public void fetchCustomDependencies_custom_too_many() throws Exception {
        String customClassName = "Something";
        String className = String.format("Map<List<Integer>, %s>", customClassName);

        when(this.searchService.findClassesWithName(customClassName)).thenReturn(Lists.newArrayList("", ""));

        this.classUnderTest.fetchDependencies(className);
    }

    @Test(expected = PlasterException.class)
    public void fetchCustomDependencies_custom_not_found() throws Exception {
        String customClassName = "Something";
        String className = String.format("Map<List<Integer>, %s>", customClassName);

        when(this.searchService.findClassesWithName(customClassName)).thenReturn(Lists.newArrayList());

        this.classUnderTest.fetchDependencies(className);
    }

    @Test
    public void fetchCustomDependencies_custom() throws Exception {
        String customClassName = "Something";
        String className = String.format("Map<List<Integer>, %s>", customClassName);

        when(this.searchService.findClassesWithName(customClassName))
                .thenReturn(Lists.newArrayList("project/module/src/main/java/com/example/Something.java"));
        when(this.configurationService.get(Setting.BASE_PATH)).thenReturn("src/main/java");

        List<JavaDependency> foundDependencies = this.classUnderTest.fetchDependencies(className);
        List<String> depStrings = foundDependencies.stream()
                .map(JavaDependency::getPath)
                .collect(Collectors.toList());

        assertThat(foundDependencies, hasSize(3));
        assertThat(depStrings, hasItems("java.util.List", "java.util.Map", "com.example.Something"));
        verify(this.searchService, times(1)).findClassesWithName(anyString());
    }

}