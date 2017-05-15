package github.jdrost1818.plaster.service.it;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.StoredJavaType;
import github.jdrost1818.plaster.domain.*;
import github.jdrost1818.plaster.service.ConfigurationServiceTest;
import github.jdrost1818.plaster.service.GenerateService;
import github.jdrost1818.plaster.service.ServiceProvider;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenerateServiceItTest {

    private GenerateService classUnderTest = ServiceProvider.getGenerateService();

    private FileInformation fileInformation;

    @Before
    public void setUp() throws Exception {
        ConfigurationServiceTest.transformToItService(ServiceProvider.getConfigurationService());

        Field id = new Field(new TypeDeclaration("List", Lists.newArrayList(StoredJavaType.LIST.getType(false))), "id");
        Field mapField = new Field(new TypeDeclaration("Map", Lists.newArrayList(StoredJavaType.MAP.getType(false))), "var1");
        Field listField = new Field(new TypeDeclaration("List", Lists.newArrayList(StoredJavaType.LIST.getType(false))), "var2");
        Field exampleField = new Field(new TypeDeclaration("Example", Lists.newArrayList(new Type("Example", new Dependency("com.example.app.Example")))), "var3");

        this.fileInformation = new FileInformation("example_class", id, Lists.newArrayList(
                mapField, listField, exampleField
        ));
    }

    @Test
    public void generateModel_lombok_enabled() throws IOException {
        String expected = GeneratedContent.MODEL_CLASS_LOMBOK_IT;

        this.classUnderTest.generateModel(this.fileInformation);
        File generatedFile = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/model/ExampleClass.java");

        assertTrue(generatedFile.exists());

        String content = new String(Files.readAllBytes(generatedFile.toPath()));

        FileUtils.deleteDirectory(new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/"));

        assertThat(content, equalTo(expected));
    }

    @Test
    public void generateModel_lombok_not_enabled() throws Exception {
        String expected = GeneratedContent.MODEL_CLASS_NO_LOMBOK_IT;
        ServiceProvider.getConfigurationService().put(Setting.IS_LOMBOK_ENABLED, "false");

        this.classUnderTest.generateModel(this.fileInformation);
        File generatedFile = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/model/ExampleClass.java");

        assertTrue(generatedFile.exists());

        String content = new String(Files.readAllBytes(generatedFile.toPath()));

        ServiceProvider.getConfigurationService().put(Setting.IS_LOMBOK_ENABLED, "true");
        FileUtils.deleteDirectory(new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/"));

        assertThat(content, equalTo(expected));
    }

    @Test
    public void generateController() throws Exception {
        String expected = GeneratedContent.CONTROLLER_CLASS_IT;

        this.classUnderTest.generateController(this.fileInformation);
        File generatedFile = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/controller/ExampleClassController.java");

        assertTrue(generatedFile.exists());

        String content = new String(Files.readAllBytes(generatedFile.toPath()));

        FileUtils.deleteDirectory(new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/"));

        assertThat(content, equalTo(expected));
    }

    @Test
    public void generateService() throws Exception {
        String expected = GeneratedContent.SERVICE_CLASS_IT;

        this.classUnderTest.generateService(this.fileInformation);
        File generatedFile = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/service/ExampleClassService.java");

        assertTrue(generatedFile.exists());

        String content = new String(Files.readAllBytes(generatedFile.toPath()));

        FileUtils.deleteDirectory(new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/"));

        assertThat(content, equalTo(expected));
    }

    @Test
    public void generateRepository() throws Exception {
        String expected = GeneratedContent.REPOSITORY_CLASS_IT;

        this.classUnderTest.generateRepository(this.fileInformation);
        File generatedFile = new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/repository/ExampleClassRepository.java");

        assertTrue(generatedFile.exists());

        String content = new String(Files.readAllBytes(generatedFile.toPath()));

        FileUtils.deleteDirectory(new File("src/test/resources/testProject/src/main/java/com/example/app/somewhere/"));

        assertThat(content, equalTo(expected));
    }

}
