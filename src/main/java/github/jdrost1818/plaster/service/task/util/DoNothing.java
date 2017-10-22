package github.jdrost1818.plaster.service.task.util;

import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.task.PlasterTask;

public class DoNothing extends PlasterTask {

    public DoNothing() {
        super("", null);
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        // The essence of this class is to do nothing, so... do nothing
        return true;
    }

    @Override
    protected PlasterTask success() {
        return null;
    }
}
