package github.jdrost1818.plaster.task.generate;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.modifier.GenerateService;
import github.jdrost1818.plaster.task.FileExecutor;
import github.jdrost1818.plaster.task.PlasterTask;

public abstract class GenerateTask extends PlasterTask {

    private static UtilityService utilityService = ServiceProvider.getUtilityService();

    static GenerateService generateService = ServiceProvider.getGenerateService();

    private final PlasterTask nextGeneration;
    private final FileExecutor fileExecutor;

    public static PlasterTask getInitialTask(ModeScope scope) {
        return new Model();
    }

    GenerateTask(String errorMsg, ModeScope scope, PlasterTask nextGeneration, FileExecutor fileExecutor) {
        super(errorMsg, scope);

        this.nextGeneration = nextGeneration;
        this.fileExecutor = fileExecutor;
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        if (!utilityService.fileExists(fileInformation, this.scope)) {
            this.fileExecutor.execute(fileInformation);
        } else {
            String infoMessage = String.format(
                    "%s already exists for scope ['%s'] - skipping generation",
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
