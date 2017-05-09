package github.jdrost1818.methods;

import github.jdrost1818.service.GenerateService;
import github.jdrost1818.service.ServiceProvider;

public class Generate {

    private static final GenerateService generateService = ServiceProvider.getGenerateService();

    public static final PlasterMethod MODEL = (generateService::generateModel);

    public static final PlasterMethod CONTROLLER = (generateService::generateController);

    public static final PlasterMethod SERVICE = (generateService::generateService);

    public static final PlasterMethod REPOSITORY = (generateService::generateRepository);

    public static final PlasterMethod FIELDS = (generateService::addFields);

}
