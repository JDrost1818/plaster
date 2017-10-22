package github.jdrost1818.plaster.service.template.controller;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.StoredJavaType;
import github.jdrost1818.plaster.domain.*;
import github.jdrost1818.plaster.domain.template.FlattenedField;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ControllerTemplateServiceTest {

    @Mock
    private ConfigurationService configurationService;

    private ControllerTemplateService classUnderTest;

    private FileInformation fileInformation;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        this.classUnderTest = new ControllerTemplateService(configurationService);

        Field id = new Field(new TypeDeclaration("List", Lists.newArrayList(StoredJavaType.LIST.getType(false))), "id");
        Field mapField = new Field(new TypeDeclaration("Map", Lists.newArrayList(StoredJavaType.MAP.getType(false))), "var1");
        Field listField = new Field(new TypeDeclaration("List", Lists.newArrayList(StoredJavaType.LIST.getType(false))), "var2");
        Field exampleField = new Field(new TypeDeclaration("Example", Lists.newArrayList(new Type("Example", new Dependency("com.example.app.Example")))), "var3");

        this.fileInformation = new FileInformation("example_class", id, Lists.newArrayList(
                mapField, listField, exampleField
        ));
    }

    @Test
    public void getTemplate() {
        JtwigTemplate foundTemplate = this.classUnderTest.getTemplate();

        assertThat(foundTemplate, notNullValue());
    }

    @Test
    public void addCustomInformation() {
        JtwigModel model = JtwigModel.newModel();

        JtwigModel modifiedModel = this.classUnderTest.addCustomInformation(model, this.fileInformation);

        assertThat(modifiedModel.get("dependencies").get().getValue(), equalTo(Lists.newArrayList(new Dependency("java.util.List"))));
        assertThat(modifiedModel.get("controllerField").get().getValue(), equalTo(new FlattenedField("", "ExampleClassController", "exampleClassController")));
        assertThat(modifiedModel.get("modelField").get().getValue(), equalTo(new FlattenedField("", "ExampleClass", "exampleClass")));
        assertThat(modifiedModel.get("serviceField").get().getValue(), equalTo(new FlattenedField("", "ExampleClassService", "exampleClassService")));
        assertThat(modifiedModel.get("idField").get().getValue(), equalTo(new FlattenedField("", "List", "id")));
        assertThat(modifiedModel.get("baseRoute").get().getValue(), equalTo("exampleClass"));
    }

    @Test
    public void renderTemplate() {
        String expected = "package com.example.app.controller.somewhere;\n" +
                "\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.http.HttpStatus;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "import com.example.app.service.somewhere.ExampleClassService;\n" +
                "import com.example.app.model.somewhere.ExampleClass;\n" +
                "\n" +
                "@RestController\n" +
                "@RequestMapping(\"/exampleClass\")\n" +
                "public class ExampleClassController {\n" +
                "\n" +
                "    private final ExampleClassService exampleClassService;\n" +
                "\n" +
                "    @Autowired\n" +
                "    public ExampleClassController(ExampleClassService exampleClassService) {\n" +
                "            this.exampleClassService = exampleClassService;\n" +
                "    }\n" +
                "\n" +
                "    @RequestMapping(method = RequestMethod.POST)\n" +
                "    @ResponseStatus(value = HttpStatus.CREATED)\n" +
                "    public ExampleClass create(@RequestBody ExampleClass exampleClass) {\n" +
                "            return this.exampleClassService.create(exampleClass);\n" +
                "    }\n" +
                "\n" +
                "    @RequestMapping(value = \"/{id}\", method = RequestMethod.GET)\n" +
                "    public ExampleClass read(@PathVariable List id) {\n" +
                "            return this.exampleClassService.read(id);\n" +
                "    }\n" +
                "\n" +
                "    @RequestMapping(value = \"/{id}\", method = RequestMethod.PUT)\n" +
                "    public ExampleClass update(@PathVariable List id, @RequestBody ExampleClass exampleClass) {\n" +
                "            return this.exampleClassService.update(exampleClass);\n" +
                "    }\n" +
                "\n" +
                "    @RequestMapping(value = \"/{id}\", method = RequestMethod.DELETE)\n" +
                "    public void delete(@PathVariable List id) {\n" +
                "            this.exampleClassService.delete(id);\n" +
                "    }\n" +
                "\n" +
                "}\n";

        when(this.configurationService.get(Setting.APP_PATH)).thenReturn("/com/example/app");
        when(this.configurationService.get(Setting.REL_MODEL_PACKAGE)).thenReturn("/model");
        when(this.configurationService.get(Setting.REL_CONTROLLER_PACKAGE)).thenReturn("/controller");
        when(this.configurationService.get(Setting.REL_SERVICE_PACKAGE)).thenReturn("/service");
        when(this.configurationService.get(Setting.SUB_DIR_PATH)).thenReturn("/somewhere");

        String actual = this.classUnderTest.renderTemplate(this.fileInformation);

        assertThat(expected, equalTo(actual));
    }
}