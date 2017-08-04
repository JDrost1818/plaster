package github.jdrost1818.plaster.task.generate;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.UtilityService;
import github.jdrost1818.plaster.task.FileExecutor;
import github.jdrost1818.plaster.task.PlasterTask;
import github.jdrost1818.plaster.task.delete.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

public class GenerateTaskTest {

    @Mock
    private UtilityService utilityService;

    @InjectMocks
    private GenerateTask generateTask = new GenerateTaskTestClass();

    private FileInformation fileInformation = new FileInformation("Test", null, Lists.newArrayList());

    @Before
    public void setup() {
        initMocks(this);
        GenerateTask.setUtilityService(this.utilityService);
    }

    @Test
    public void getInitialTask() throws Exception {
        for (ModeScope curScope : ModeScope.values()) {
            assertThat(GenerateTask.getInitialTask(curScope), instanceOf(Model.class));
        }
    }

    @Test
    public void execute_should_generate() throws Exception {
        when(this.utilityService.fileExists(this.fileInformation, ModeScope.MODEL)).thenReturn(false);

        this.generateTask.perform(this.fileInformation, ModeScope.MODEL);

        verify(this.generateTask.fileExecutor, times(1)).execute(this.fileInformation);
    }

    @Test
    public void execute_should_not_generate() throws Exception {
        when(this.utilityService.fileExists(this.fileInformation, ModeScope.MODEL)).thenReturn(true);

        this.generateTask.perform(this.fileInformation, ModeScope.MODEL);

        verify(this.generateTask.fileExecutor, times(0)).execute(this.fileInformation);
    }

    @Test
    public void execute_runs_success() throws Exception {
        when(this.utilityService.fileExists(this.fileInformation, ModeScope.MODEL)).thenReturn(false);

        this.generateTask.perform(this.fileInformation, ModeScope.SERVICE);

        verify(this.generateTask.nextGeneration, times(1)).perform(this.fileInformation, ModeScope.SERVICE);
    }

    private static class GenerateTaskTestClass extends GenerateTask {

        GenerateTaskTestClass() {
            super("Could not generate model",
                    ModeScope.MODEL,
                    mock(PlasterTask.class),
                    mock(FileExecutor.class));
        }

    }

}