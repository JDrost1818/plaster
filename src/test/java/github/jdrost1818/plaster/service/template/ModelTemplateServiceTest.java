package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.domain.template.FlattenedField;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.jtwig.JtwigModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ModelTemplateServiceTest {

    @Mock
    private ConfigurationService configurationService;

    private ModelTemplateService classUnderTest;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        this.classUnderTest = new ModelTemplateService(configurationService);
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

}