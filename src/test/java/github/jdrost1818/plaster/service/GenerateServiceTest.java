package github.jdrost1818.plaster.service;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.template.ControllerTemplateService;
import github.jdrost1818.plaster.service.template.ModelTemplateService;
import github.jdrost1818.plaster.service.template.RepositoryTemplateService;
import github.jdrost1818.plaster.service.template.ServiceTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class GenerateServiceTest {

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private ModelTemplateService modelTemplateService;

    @Mock
    private ControllerTemplateService controllerTemplateService;

    @Mock
    private ServiceTemplateService serviceTemplateService;

    @Mock
    private RepositoryTemplateService repositoryTemplateService;

    private GenerateService generateService;

    private FileInformation fileInformation;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        this.generateService = new GenerateService(
                this.configurationService,
                this.modelTemplateService,
                this.controllerTemplateService,
                this.serviceTemplateService,
                this.repositoryTemplateService);

        this.fileInformation = new FileInformation("ExampleClass", null, Lists.newArrayList());
    }

    @Test
    public void generateModel() throws Exception {

    }

    @Test
    public void generateController() throws Exception {

    }

    @Test
    public void generateService() throws Exception {

    }

    @Test
    public void generateRepository() throws Exception {

    }

    @Test
    public void addFields() throws Exception {

    }

}