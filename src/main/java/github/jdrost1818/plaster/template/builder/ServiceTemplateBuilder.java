package github.jdrost1818.plaster.template.builder;


import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.template.TemplateBuilder;
import github.jdrost1818.plaster.template.TemplateUtil;
import org.jtwig.JtwigModel;

public class ServiceTemplateBuilder  extends TemplateBuilder {

    private static ServiceTemplateBuilder instance = new ServiceTemplateBuilder();

    public static ServiceTemplateBuilder getInstance() {
        return instance;
    }

    @Override
    public JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
        model = TemplateUtil.addTypeField(model, genTypeModel, TemplateType.MODEL);
        model = TemplateUtil.addTypeField(model, genTypeModel, TemplateType.SERVICE);
        model = TemplateUtil.addTypeField(model, genTypeModel, TemplateType.REPOSITORY);
        model = TemplateUtil.addDependencies(model, fileInformation.getId());
        model = TemplateUtil.addId(model, fileInformation);

        return model;
    }

}