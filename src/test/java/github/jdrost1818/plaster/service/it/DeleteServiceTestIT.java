package github.jdrost1818.plaster.service.it;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationServiceTest;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.modifier.DeleteService;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteServiceTestIT {

    private DeleteService classUnderTest = ServiceProvider.getDeleteService();

    private FileInformation fileInformation;

    @Before
    public void setUp() throws Exception {
        ConfigurationServiceTest.transformToItService(ServiceProvider.getConfigurationService());

        this.fileInformation = new FileInformation("example_class", null, Lists.newArrayList());
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/"));
    }

    @Test
    public void modifyModel() throws Exception {
        File fileToDelete = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/model/ExampleClass.java");
        FileUtils.writeLines(fileToDelete, Lists.newArrayList("This file will be deleted"));

        assertTrue(fileToDelete.exists());

        this.classUnderTest.modifyModel(this.fileInformation);

        assertFalse(fileToDelete.exists());
    }

    @Test
    public void modifyService() throws Exception {
        File fileToDelete = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/service/ExampleClassService.java");
        FileUtils.writeLines(fileToDelete, Lists.newArrayList("This file will be deleted"));

        assertTrue(fileToDelete.exists());

        this.classUnderTest.modifyService(this.fileInformation);

        assertFalse(fileToDelete.exists());
    }

    @Test
    public void modifyController() throws Exception {
        File fileToDelete = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/controller/ExampleClassController.java");
        FileUtils.writeLines(fileToDelete, Lists.newArrayList("This file will be deleted"));

        assertTrue(fileToDelete.exists());

        this.classUnderTest.modifyController(this.fileInformation);

        assertFalse(fileToDelete.exists());
    }

    @Test
    public void modifyRepository() throws Exception {
        File fileToDelete = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/repository/ExampleClassRepository.java");
        FileUtils.writeLines(fileToDelete, Lists.newArrayList("This file will be deleted"));

        assertTrue(fileToDelete.exists());

        this.classUnderTest.modifyRepository(this.fileInformation);

        assertFalse(fileToDelete.exists());
    }

}