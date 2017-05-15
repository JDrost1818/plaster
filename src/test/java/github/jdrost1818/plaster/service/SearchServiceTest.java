package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.Setting;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SearchServiceTest {

    @Mock
    ConfigurationService configurationService;

    @InjectMocks
    SearchService classUnderTest;

    String resourceDir;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        this.classUnderTest = new SearchService(configurationService);

        File file = new File(getClass().getClassLoader().getResource("testProject").getFile());
        this.resourceDir = file.getAbsolutePath();
    }

    @Test
    public void findClassesWithName_multiple_2_point_oh() throws Exception {
        when(this.configurationService.get(Setting.PROJECT_PATH)).thenReturn(this.resourceDir);
        when(this.configurationService.get(Setting.BASE_PATH)).thenReturn("src/main/java");
        when(this.configurationService.get(Setting.APP_PATH)).thenReturn("com/example/app");

        List<String> foundPaths = this.classUnderTest.findClassesWithName("duplicate");

        assertThat(foundPaths, hasSize(2));
        assertThat("com/example/app/dir1/Duplicate.java", isIn(foundPaths));
        assertThat("com/example/app/dir2/Duplicate.java", isIn(foundPaths));
    }

    @Test
    public void findClassesWithName_start_with_file() throws Exception {
        when(this.configurationService.get(Setting.PROJECT_PATH)).thenReturn("/" + this.resourceDir + "/testProject/root1/pom.xml");

        List<String> foundPaths = this.classUnderTest.findClassesWithName("duplicate");

        assertThat(foundPaths, empty());
    }

}