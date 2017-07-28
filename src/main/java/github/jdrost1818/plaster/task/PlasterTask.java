package github.jdrost1818.plaster.task;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;

public abstract class PlasterTask {

    private final String errorMsg;
    protected final ModeScope scope;

    public PlasterTask(String errorMsg, ModeScope scope) {
        this.errorMsg = errorMsg;
        this.scope = scope;
    }

    public void perform(FileInformation fileInformation, ModeScope maxGenScope) {
        if (this.execute(fileInformation)) {
            if (this.scope == maxGenScope) {
                this.finish();
            } else {
                this.success(fileInformation, maxGenScope);
            }
        } else {
            this.failure();
        }
    }

    protected abstract boolean execute(FileInformation fileInformation);

    protected abstract void success(FileInformation fileInformation, ModeScope maxGenScope);

    protected void failure() {
        failure(this.errorMsg);
    }

    protected void failure(String message) {
        throw new PlasterException(message);
    }

    protected void finish() {

    }

}
