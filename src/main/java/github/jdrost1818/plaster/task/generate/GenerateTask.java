package github.jdrost1818.plaster.task.generate;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.modifier.GenerateService;
import github.jdrost1818.plaster.task.FileExecutor;
import github.jdrost1818.plaster.task.PlasterTask;

/**
 * Defines a common method of execution for tasks which should generate new files
 */
public abstract class GenerateTask extends PlasterTask {

    private static UtilityService utilityService = ServiceProvider.getUtilityService();

    static GenerateService generateService = ServiceProvider.getGenerateService();

    private final PlasterTask nextGeneration;
    private final FileExecutor fileExecutor;

    public static PlasterTask getInitialTask(ModeScope scope) {
        return new Model();
    }

    /**
     *
     *
     * @param errorMsg
     *          message to display on a failure
     * @param scope
     *          the scope the task is tied to
     * @param nextGeneration
     *          the next task to perform on success
     * @param fileExecutor
     *          to be used to generate the file
     */
    GenerateTask(String errorMsg, ModeScope scope, PlasterTask nextGeneration, FileExecutor fileExecutor) {
        super(errorMsg, scope);

        this.nextGeneration = nextGeneration;
        this.fileExecutor = fileExecutor;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void success(FileInformation fileInformation, ModeScope maxGenScope) {
        this.nextGeneration.perform(fileInformation, maxGenScope);
    }
}
