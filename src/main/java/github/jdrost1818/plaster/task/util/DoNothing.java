package github.jdrost1818.plaster.task.util;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.task.PlasterTask;

public class DoNothing extends PlasterTask {

    public DoNothing() {
        super("", null);
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        return true;
    }

    @Override
    protected void success(FileInformation fileInformation, ModeScope maxGenScope) {

    }
}
