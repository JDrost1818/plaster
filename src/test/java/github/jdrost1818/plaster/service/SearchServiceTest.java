package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.Setting;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by jake on 5/9/17.
 */
public class SearchServiceTest {

    @Mock
    ConfigurationService configurationService;

    SearchService classUnderTest = ServiceProvider.getSearchService();

    String resourceDir;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        this.classUnderTest.setConfigurationService(this.configurationService);

        File file = new File(getClass().getClassLoader().getResource("testProject").getFile());
        this.resourceDir = file.getAbsolutePath();
    }

    @Test
    public void findClassesWithName_multiple() throws Exception {
        when(this.configurationService.get(Setting.BASE_PATH)).thenReturn(this.resourceDir);

        List<String> foundPaths = this.classUnderTest.findClassesWithName("duplicate");

        assertThat(foundPaths, hasSize(2));
        assertThat("src/main/java/com/example/dir1/Duplicate.java", isIn(foundPaths));
        assertThat("src/main/java/com/example/dir2/Duplicate.java", isIn(foundPaths));
    }

    @Test
    public void findClassesWithName_start_with_file() throws Exception {
        when(this.configurationService.get(Setting.BASE_PATH)).thenReturn(this.resourceDir + "/testProject/pom.xml");

        List<String> foundPaths = this.classUnderTest.findClassesWithName("duplicate");

        assertThat(foundPaths, empty());
    }

}