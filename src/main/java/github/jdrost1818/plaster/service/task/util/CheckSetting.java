package github.jdrost1818.plaster.service.task.util;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.PlasterTask;

public class CheckSetting extends PlasterTask {

    private final ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    private final Setting setting;

    private final PlasterTask trueTask;

    private final PlasterTask falseTask;

    public CheckSetting(Setting settingToCheck, PlasterTask taskIfEnabled, PlasterTask taskIfNotEnabled) {
        super("Setting: " + settingToCheck.name() + " is not of boolean type", null);

        this.setting = settingToCheck;
        this.trueTask = taskIfEnabled;
        this.falseTask = taskIfNotEnabled;
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        return true;
    }

    @Override
    protected PlasterTask success() {
        return configurationService.getBoolean(this.setting) ? this.trueTask : this.falseTask;
    }
}
