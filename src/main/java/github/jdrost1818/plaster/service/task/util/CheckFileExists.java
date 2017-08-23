package github.jdrost1818.plaster.service.task.util;

import github.jdrost1818.plaster.data.TemplateType;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.task.PlasterTask;

import static java.util.Objects.nonNull;

public class CheckFileExists extends PlasterTask {

    private final UtilityService utilityService = ServiceProvider.getUtilityService();

    private final TemplateType templateType;

    private final PlasterTask trueTask;

    private final PlasterTask falseTask;

    private FileInformation fileInformation;

    public CheckFileExists(TemplateType templateType, PlasterTask taskIfExists, PlasterTask taskIfDoesNotExist) {
        super("", null);

        this.templateType = templateType;
        this.trueTask = taskIfExists;
        this.falseTask = taskIfDoesNotExist;
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        this.fileInformation = fileInformation;

        return true;
    }

    @Override
    protected PlasterTask success() {
        if (nonNull(this.fileInformation) && this.utilityService.fileExists(this.fileInformation, this.templateType)) {
            return trueTask;
        }
        return this.falseTask;
    }

}
