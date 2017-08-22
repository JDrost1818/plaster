package github.jdrost1818.plaster.it;

import github.jdrost1818.plaster.Plaster;
import github.jdrost1818.plaster.service.ConfigurationServiceTest;
import github.jdrost1818.plaster.service.ServiceProvider;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlasterIt {

    @Test
    public void main() throws Exception {
        ConfigurationServiceTest.transformToItService(ServiceProvider.getConfigurationService());

        String[] args = new String[] {
                "g",
                "scaffold",
                "-k key:string",
                "-d someplace",
                "example_class",
                "var1:map",
                "var2:List",
                "var3:example"
        };


        Plaster.main(args);

        File genModelFile = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/model/someplace/ExampleClass.java");
        File genControllerFile = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/controller/someplace/ExampleClassController.java");
        File genServiceFile = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/service/someplace/ExampleClassService.java");
        File genRepositoryFile = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/repository/someplace/ExampleClassRepository.java");

        assertTrue(genModelFile.exists());
        assertTrue(genControllerFile.exists());
        assertTrue(genServiceFile.exists());
        assertTrue(genRepositoryFile.exists());

        String modelContent = new String(Files.readAllBytes(genModelFile.toPath()));
        String controllerContent = new String(Files.readAllBytes(genControllerFile.toPath()));
        String serviceContent = new String(Files.readAllBytes(genServiceFile.toPath()));
        String repositoryContent = new String(Files.readAllBytes(genRepositoryFile.toPath()));

        assertThat(GeneratedContent.MODEL_CLASS_LOMBOK_IT, equalTo(modelContent));
        assertThat(GeneratedContent.CONTROLLER_CLASS_IT, equalTo(controllerContent));
        assertThat(GeneratedContent.SERVICE_CLASS_IT, equalTo(serviceContent));
        assertThat(GeneratedContent.REPOSITORY_CLASS_IT, equalTo(repositoryContent));
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/"));
        FileUtils.deleteDirectory(new File("src/test/resources/testProject/src/test/java/com/example/app/somewhere/"));
    }

}
