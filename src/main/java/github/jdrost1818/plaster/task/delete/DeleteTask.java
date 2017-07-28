package github.jdrost1818.plaster.task.delete;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.modifier.DeleteService;
import github.jdrost1818.plaster.task.FileExecutor;
import github.jdrost1818.plaster.task.PlasterTask;

public abstract class DeleteTask extends PlasterTask {

    private static UtilityService utilityService = ServiceProvider.getUtilityService();

    static DeleteService deleteService = ServiceProvider.getDeleteService();

    private final PlasterTask nextGeneration;
    private final FileExecutor fileExecutor;

    public static PlasterTask getInitialTask(ModeScope scope) {
        return new Controller();
    }

    DeleteTask(String errorMsg, ModeScope scope, PlasterTask nextGeneration, FileExecutor fileExecutor) {
        super(errorMsg, scope);

        this.nextGeneration = nextGeneration;
        this.fileExecutor = fileExecutor;
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        try {
            if (utilityService.fileExists(fileInformation, this.scope)) {
                this.fileExecutor.execute(fileInformation);
            } else {
                String infoMessage = String.format(
                        "%s could not be found for scope ['%s'] - skipping deletion",
                        fileInformation.getClassName(),
                        this.scope.name());

                System.out.println(infoMessage);
            }
        } catch (PlasterException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    protected void success(FileInformation fileInformation, ModeScope maxGenScope) {
        this.nextGeneration.perform(fileInformation, maxGenScope);
    }
}
