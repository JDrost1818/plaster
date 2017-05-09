package github.jdrost1818.methods;

import github.jdrost1818.service.GenerateService;

public class Generate {

    public static final PlasterMethod MODEL = (GenerateService::generateModel);

    public static final PlasterMethod CONTROLLER = (GenerateService::generateController);

    public static final PlasterMethod SERVICE = (GenerateService::generateService);

    public static final PlasterMethod REPOSITORY = (GenerateService::generateRepository);

    public static final PlasterMethod FIELDS = (GenerateService::addFields);

}
