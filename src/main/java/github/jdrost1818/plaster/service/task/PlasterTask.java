package github.jdrost1818.plaster.service.task;

import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;

public abstract class PlasterTask {

    private final String errorMsg;
    protected final PlasterTaskId taskId;

    public PlasterTask(String errorMsg, PlasterTaskId taskId) {
        this.errorMsg = errorMsg;
        this.taskId = taskId;
    }

    protected abstract boolean execute(FileInformation fileInformation);

    protected abstract PlasterTask success();

    /**
     * Throws an exception with the given text
     *
     * @throws PlasterException with the default message
     */
    protected void failure() {
        this.failure(this.errorMsg);
    }

    /**
     * Throws an exception with the given text
     *
     * @param message
     *          text to associate with the exception
     * @throws PlasterException with the corresponding message
     */
    protected void failure(String message) {
        throw new PlasterException(message);
    }

    protected void finish() {
        // Todo: wrap up things in the finish.
        // the main thought is to make all the generations be "pending"
        // generations until finished. This might have other purposes though too.
    }

}
