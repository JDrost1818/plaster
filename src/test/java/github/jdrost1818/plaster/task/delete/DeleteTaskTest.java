package github.jdrost1818.plaster.task.delete;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.service.modifier.DeleteService;
import github.jdrost1818.plaster.task.FileExecutor;
import github.jdrost1818.plaster.task.PlasterTask;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static github.jdrost1818.plaster.task.delete.DeleteTask.deleteService;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteTaskTest {

    @Mock
    private UtilityService utilityService;

    @InjectMocks
    private DeleteTask deleteTask = new DeleteTaskTestClass();

    private FileInformation fileInformation = new FileInformation("Test", null, Lists.newArrayList());

    @Before
    public void setup() {
        initMocks(this);
        DeleteTask.setUtilityService(this.utilityService);
    }

    @Test
    public void getInitialTask() throws Exception {
        for (ModeScope curScope : ModeScope.values()) {
            assertThat(DeleteTask.getInitialTask(curScope), instanceOf(Controller.class));
        }
    }

    @Test
    public void execute_should_delete() throws Exception {
        when(this.utilityService.fileExists(this.fileInformation, ModeScope.CONTROLLER)).thenReturn(true);

        this.deleteTask.perform(this.fileInformation, ModeScope.CONTROLLER);

        verify(this.deleteTask.fileExecutor, times(1)).execute(this.fileInformation);
    }

    @Test
    public void execute_should_not_delete() throws Exception {
        when(this.utilityService.fileExists(this.fileInformation, ModeScope.CONTROLLER)).thenReturn(false);

        this.deleteTask.perform(this.fileInformation, ModeScope.CONTROLLER);

        verify(this.deleteTask.fileExecutor, times(0)).execute(this.fileInformation);
    }

    @Test
    public void execute_runs_success() throws Exception {
        when(this.utilityService.fileExists(this.fileInformation, ModeScope.CONTROLLER)).thenReturn(false);

        this.deleteTask.perform(this.fileInformation, ModeScope.MODEL);

        verify(this.deleteTask.nextGeneration, times(1)).perform(this.fileInformation, ModeScope.MODEL);
    }

    private static class DeleteTaskTestClass extends DeleteTask {

        DeleteTaskTestClass() {
            super("Could not delete controller",
                    ModeScope.CONTROLLER,
                    mock(PlasterTask.class),
                    mock(FileExecutor.class));
        }

    }

}