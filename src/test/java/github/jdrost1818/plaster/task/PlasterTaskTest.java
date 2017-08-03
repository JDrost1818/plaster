package github.jdrost1818.plaster.task;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import lombok.Builder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlasterTaskTest {

    @Mock
    private PlasterTask task;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void perform_success() throws Exception {
        this.task = new PlasterTask("", ModeScope.CONTROLLER) {
            @Override
            protected boolean execute(FileInformation fileInformation) {
                return true;
            }

            @Override
            protected void success(FileInformation fileInformation, ModeScope maxGenScope) {
                assertTrue(true);
            }

            @Override
            protected void finish() {
                fail("SHOULD NOT ENTER FINISH STATE");
            }

            @Override
            protected void failure() {
                fail("SHOULD NOT ENTER FAILURE STATE");
            }
        };

        this.task.perform(null, ModeScope.MODEL);
    }

    @Test
    public void perform_finish() {
        this.task = new PlasterTask("", ModeScope.MODEL) {
            @Override
            protected boolean execute(FileInformation fileInformation) {
                return true;
            }

            @Override
            protected void success(FileInformation fileInformation, ModeScope maxGenScope) {
                fail("SHOULD NOT ENTER SUCCESS STATE");
            }

            @Override
            protected void finish() {
                assertTrue(true);
            }

            @Override
            protected void failure() {
                fail("SHOULD NOT ENTER FAILURE STATE");
            }
        };

        this.task.perform(null, ModeScope.MODEL);
    }

    @Test
    public void perform_failure() {
        this.task = new PlasterTask("", ModeScope.MODEL) {
            @Override
            protected boolean execute(FileInformation fileInformation) {
                return false;
            }

            @Override
            protected void success(FileInformation fileInformation, ModeScope maxGenScope) {
                fail("SHOULD NOT ENTER SUCCESS STATE");
            }

            @Override
            protected void finish() {
                fail("SHOULD NOT ENTER FINISH STATE");
            }

            @Override
            protected void failure() {
                assertTrue(true);
            }
        };

        this.task.perform(null, ModeScope.MODEL);
    }

}