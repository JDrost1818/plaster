package github.jdrost1818.plaster.task;

import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
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

    @Test
    public void failure() throws Exception {
        this.task = new PlasterTask("error message", ModeScope.MODEL) {

            @Override
            protected boolean execute(FileInformation fileInformation) {
                return false;
            }

            @Override
            protected void success(FileInformation fileInformation, ModeScope maxGenScope) {
                fail("SHOULD NOT ENTER SUCCESS STATE");
            }
        };

        try {
            this.task.failure();
        } catch (PlasterException e) {
            assertThat(e.getMessage(), equalTo("error message"));
            return;
        }

        fail();
    }

    @Test
    public void custom_failure() throws Exception {
        this.task = new PlasterTask("", ModeScope.MODEL) {

            @Override
            protected boolean execute(FileInformation fileInformation) {
                return false;
            }

            @Override
            protected void success(FileInformation fileInformation, ModeScope maxGenScope) {
                fail("SHOULD NOT ENTER SUCCESS STATE");
            }
        };

        try {
            this.task.failure("This is a custom failure");
        } catch (PlasterException e) {
            assertThat(e.getMessage(), equalTo("This is a custom failure"));
            return;
        }

        fail();
    }

}