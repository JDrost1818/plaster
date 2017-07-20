package github.jdrost1818.plaster.method;

import github.jdrost1818.plaster.service.type.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Modify {

    public static final PlasterMethod MODEL = (PlasterMethod<ModelModifier>) ModelModifier::modifyModel;

    public static final PlasterMethod CONTROLLER = (PlasterMethod<ControllerModifier>) ControllerModifier::modifyController;

    public static final PlasterMethod SERVICE = (PlasterMethod<ServiceModifier>) ServiceModifier::modifyService;

    public static final PlasterMethod REPOSITORY = (PlasterMethod<RepositoryModifier>) RepositoryModifier::modifyRepository;

}
