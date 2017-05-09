package github.jdrost1818.service;

import github.jdrost1818.data.Setting;
import github.jdrost1818.exception.PlasterException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DependencyServiceTest {

    @Mock
    ConfigurationService configurationService;

    DependencyService classUnderTest = ServiceProvider.getDependencyService();

    @Before
    public void setUp() {
        initMocks(this);

        this.classUnderTest.setConfigurationService(this.configurationService);
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


}