package github.jdrost1818.plaster.service.template;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.StoredJavaType;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.*;
import github.jdrost1818.plaster.domain.template.FlattenedField;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ModelTemplateServiceTest {

    @Mock
    private ConfigurationService configurationService;

    private ModelTemplateService classUnderTest;

    private FileInformation fileInformation;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        this.classUnderTest = new ModelTemplateService(configurationService);

        Field id = new Field(new TypeDeclaration("List", Lists.newArrayList(StoredJavaType.LIST.getType(false))), "id");
        Field mapField = new Field(new TypeDeclaration("Map", Lists.newArrayList(StoredJavaType.MAP.getType(false))), "var1");
        Field listField = new Field(new TypeDeclaration("List", Lists.newArrayList(StoredJavaType.LIST.getType(false))), "var2");
        Field exampleField = new Field(new TypeDeclaration("Example", Lists.newArrayList(new Type("Example", new Dependency("com.example.app.Example")))), "var3");

        this.fileInformation = new FileInformation("example_class", id, Lists.newArrayList(
                mapField, listField, exampleField
        ));
    }

    @Test
    public void addTypeField() throws Exception {
        JtwigModel model = JtwigModel.newModel();

        GenTypeModel genTypeModel = new GenTypeModel("example_class", false);

        when(this.configurationService.get(Setting.APP_PATH)).thenReturn("/com/example/app");
        when(this.configurationService.get(Setting.REL_MODEL_PACKAGE)).thenReturn("/model");
        when(this.configurationService.get(Setting.SUB_DIR_PATH)).thenReturn("/somewhere");

        JtwigModel modifiedModel = this.classUnderTest.addTypeField(model, genTypeModel, TemplateType.MODEL);

        FlattenedField x = (FlattenedField) modifiedModel.get("modelField").get().getValue();

        assertThat(x.getClassName(), equalTo("ExampleClass"));
        assertThat(x.getPackagePath(), equalTo("com.example.app.model.somewhere"));
        assertThat(x.getVarName(), equalTo("exampleClass"));
    }

    @Test
    public void getTemplate() {
        JtwigTemplate foundTemplate = this.classUnderTest.getTemplate();

        assertThat(foundTemplate, notNullValue());
    }

    @Test
    public void addCustomInformation_lombok_enabled() {
        JtwigModel model = JtwigModel.newModel();
        GenTypeModel genTypeModel = new GenTypeModel("example_class", true);

        JtwigModel modifiedModel = this.classUnderTest.addCustomInformation(model, this.fileInformation, genTypeModel);

        List<FlattenedField> addedFields = (List<FlattenedField>) modifiedModel.get("fields").get().getValue();
        List<Dependency> dependencies = (List<Dependency>)modifiedModel.get("dependencies").get().getValue();

        assertThat(dependencies, hasSize(3));
        assertThat(modifiedModel.get("modelField").get().getValue(), equalTo(new FlattenedField("", "ExampleClass", "exampleClass")));
        assertThat(modifiedModel.get("idField").get().getValue(), equalTo(new FlattenedField("", "List", "id")));
        assertTrue(modifiedModel.get("header").isPresent());
        assertFalse(modifiedModel.get("methods").isPresent());
        assertThat(addedFields, hasItems(
                new FlattenedField("", "Map", "var1"),
                new FlattenedField("", "List", "var2"),
                new FlattenedField("", "Example", "var3")
        ));
    }

    @Test
    public void addCustomInformation_lombok_not_enabled() {
        JtwigModel model = JtwigModel.newModel();
        GenTypeModel genTypeModel = new GenTypeModel("example_class", false);

        JtwigModel modifiedModel = this.classUnderTest.addCustomInformation(model, this.fileInformation, genTypeModel);

        List<FlattenedField> addedFields = (List<FlattenedField>) modifiedModel.get("fields").get().getValue();
        List<Dependency> dependencies = (List<Dependency>)modifiedModel.get("dependencies").get().getValue();

        assertThat(dependencies, hasSize(3));
        assertThat(modifiedModel.get("modelField").get().getValue(), equalTo(new FlattenedField("", "ExampleClass", "exampleClass")));
        assertThat(modifiedModel.get("idField").get().getValue(), equalTo(new FlattenedField("", "List", "id")));
        assertFalse(modifiedModel.get("header").isPresent());
        assertTrue(modifiedModel.get("methods").isPresent());
        assertThat(addedFields, hasItems(
                new FlattenedField("", "Map", "var1"),
                new FlattenedField("", "List", "var2"),
                new FlattenedField("", "Example", "var3")
        ));
    }

}