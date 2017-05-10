package github.jdrost1818.plaster.method;

import github.jdrost1818.plaster.service.DeleteService;
import github.jdrost1818.plaster.service.ServiceProvider;

public class Delete {

    private static final DeleteService deleteService = ServiceProvider.getDeleteService();

    public static final PlasterMethod MODEL = (deleteService::deleteModel);

    public static final PlasterMethod CONTROLLER = (deleteService::deleteController);

    public static final PlasterMethod SERVICE = (deleteService::deleteService);

    public static final PlasterMethod REPOSITORY = (deleteService::deleteRepository);

}
