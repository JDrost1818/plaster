package github.jdrost1818.plaster.task.generate;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.modifier.GenerateService;
import github.jdrost1818.plaster.task.FileExecutor;
import github.jdrost1818.plaster.task.PlasterTask;
import github.jdrost1818.plaster.task.util.Fail;
import github.jdrost1818.plaster.task.util.FileExistsCheck;

public abstract class GenerateTask extends PlasterTask {

    static GenerateService generateService = ServiceProvider.getGenerateService();

    private final PlasterTask nextGeneration;
    private final FileExecutor fileExecutor;

    GenerateTask(String errorMsg, ModeScope scope, PlasterTask nextGeneration, FileExecutor fileExecutor) {
        super(errorMsg, scope);

        this.nextGeneration = nextGeneration;
        this.fileExecutor = fileExecutor;
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        try {
            this.fileExecutor.generate(fileInformation);
        } catch (PlasterException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    protected void success(FileInformation fileInformation) {
        FileExistsCheck.builder()
                .onFileDoesNotExist(this.nextGeneration)
                .onFileExists(new Fail("Cannot generate file that already exists", null))
                .build()
                    .perform(fileInformation, null);
    }
}
