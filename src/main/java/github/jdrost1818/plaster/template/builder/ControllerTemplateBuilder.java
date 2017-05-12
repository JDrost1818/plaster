package github.jdrost1818.plaster.template.builder;


import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.domain.GenTypeModel;
import github.jdrost1818.plaster.template.TemplateBuilder;
import github.jdrost1818.plaster.template.TemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.JtwigModel;

public class ControllerTemplateBuilder  extends TemplateBuilder {

    private static ControllerTemplateBuilder instance = new ControllerTemplateBuilder();

    public static ControllerTemplateBuilder getInstance() {
        return instance;
    }

    @Override
    public JtwigModel addCustomInformation(JtwigModel model, FileInformation fileInformation, GenTypeModel genTypeModel) {
        model = TemplateUtil.addTypeField(model, genTypeModel, TemplateType.CONTROLLER);
        model = TemplateUtil.addTypeField(model, genTypeModel, TemplateType.MODEL);
        model = TemplateUtil.addTypeField(model, genTypeModel, TemplateType.SERVICE);
        model = TemplateUtil.addDependencies(model, fileInformation);
        model = TemplateUtil.addId(model, fileInformation);
        model = addBaseRoute(model, fileInformation);

        return model;
    }

    private JtwigModel addBaseRoute(JtwigModel model, FileInformation fileInformation) {
        return model.with("baseRoute", StringUtils.uncapitalize(fileInformation.getClassName()));
    }

}