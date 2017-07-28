package github.jdrost1818.plaster.task.util;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.task.PlasterTask;

import java.nio.file.Paths;

public class FileExistsCheck extends PlasterTask {

    private final ModeScope scope;
    private final PlasterTask onFileExists;
    private final PlasterTask onFileDoesNotExist;

    private FileInformation fileInformation;


    public FileExistsCheck(ModeScope scope, PlasterTask onFileExists, PlasterTask onFileDoesNotExist) {
        super("", null);

        this.scope = scope;
        this.onFileExists = onFileExists;
        this.onFileDoesNotExist = onFileDoesNotExist;
    }

    @Override
    public void perform(FileInformation fileInformation, ModeScope scope) {
        this.fileInformation = fileInformation;

        super.perform(fileInformation, scope);
    }

    @Override
    protected boolean execute(FileInformation fileInformation) {
        return Paths.get("").toFile().exists();
    }

    @Override
    protected void success(FileInformation fileInformation) {
        this.onFileExists.perform(fileInformation, this.scope);
    }

    @Override
    protected void failure(String msg) {
        this.onFileDoesNotExist.perform(this.fileInformation, this.scope);
    }

}
