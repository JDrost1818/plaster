package github.jdrost1818.methods;

import github.jdrost1818.service.DeleteService;

public class Delete {

    public static final PlasterMethod MODEL = (DeleteService::deleteModel);

    public static final PlasterMethod CONTROLLER = (DeleteService::deleteController);

    public static final PlasterMethod SERVICE = (DeleteService::deleteService);

    public static final PlasterMethod REPOSITORY = (DeleteService::deleteRepository);

}
