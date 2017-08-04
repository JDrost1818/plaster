package github.jdrost1818.plaster.task.delete;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.modifier.DeleteService;
import github.jdrost1818.plaster.task.FileExecutor;
import github.jdrost1818.plaster.task.PlasterTask;
import lombok.Setter;

public abstract class DeleteTask extends PlasterTask {

    @Setter
    private static UtilityService utilityService = ServiceProvider.getUtilityService();

    @Setter
    static DeleteService deleteService = ServiceProvider.getDeleteService();

    final PlasterTask nextGeneration;

    final FileExecutor fileExecutor;

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
        if (utilityService.fileExists(fileInformation, this.scope)) {
            this.fileExecutor.execute(fileInformation);
        } else {
            String infoMessage = String.format(
                    "%s could not be found for scope ['%s'] - skipping deletion",
                    fileInformation.getClassName(),
                    this.scope.name());

            System.out.println(infoMessage);
        }

        return true;
    }

    @Override
    protected void success(FileInformation fileInformation, ModeScope maxGenScope) {
        this.nextGeneration.perform(fileInformation, maxGenScope);
    }

}
