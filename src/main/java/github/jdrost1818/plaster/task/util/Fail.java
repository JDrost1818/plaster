package github.jdrost1818.plaster.task.util;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.task.PlasterTask;

public class Fail extends PlasterTask {

    public Fail(String errorMsg, ModeScope scope) {
        super(errorMsg, scope);
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        return false;
    }

    @Override
    protected void success(FileInformation fileInformation) {

    }
}
