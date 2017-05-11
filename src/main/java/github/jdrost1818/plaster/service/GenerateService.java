package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.template.builder.ModelTemplateBuilder;
import github.jdrost1818.plaster.util.PathUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.OutputStream;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class GenerateService {

    @Setter
    private ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    public void generateModel(FileInformation fileInformation) {
        GenTypeModel genTypeModel = new GenTypeModel(
                PathUtil.pathToPackage(this.configurationService.get(Setting.REL_MODEL_PACKAGE)),
                fileInformation.getClassName(),
                this.configurationService.getBoolean(Setting.IS_LOMBOK_ENABLED));

        ModelTemplateBuilder.getInstance().renderTemplate(
                "location",
                fileInformation,
                genTypeModel,
                getOutputStream("location"));
    }
    
    public void generateController(FileInformation fileInformation) {
        
    }
    
    public void generateService(FileInformation fileInformation) {
        
    }
    
    public void generateRepository(FileInformation fileInformation) {
        
    }
    
    public void addFields(FileInformation fileInformation) {
        
    }

    private OutputStream getOutputStream(String location) {
        return null;
    }
    
}
