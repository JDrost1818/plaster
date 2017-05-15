package github.jdrost1818.plaster.service.template;

import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.service.ConfigurationService;
import org.jtwig.JtwigModel;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class TemplateServiceTest {

    @Mock
    private ConfigurationService configurationService;

    private TemplateService templateService;

    FileInformation fileInformation;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        this.templateService = new TemplateServiceImpl(configurationService);
    }

    private class TemplateServiceImpl extends TemplateService {

        public TemplateServiceImpl(ConfigurationService configurationService) {
            super(configurationService);
        }

        @Override
        public JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
            return null;
        }
    }

}