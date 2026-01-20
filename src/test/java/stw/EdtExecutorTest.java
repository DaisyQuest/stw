package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.SwingUtilities;
import org.junit.jupiter.api.Test;

class EdtExecutorTest {
    @Test
    void runsOnEdtWhenCalledOffThread() {
        String value = EdtExecutor.runOnEdtAndWait(() -> {
            assertTrue(SwingUtilities.isEventDispatchThread());
            return "ok";
        });

        assertEquals("ok", value);
    }

    @Test
    void runsInlineWhenAlreadyOnEdt() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            String value = EdtExecutor.runOnEdtAndWait(() -> "inline");
            assertEquals("inline", value);
        });
    }

    @Test
    void wrapsExceptions() {
        EdtExecutionException ex = assertThrows(EdtExecutionException.class,
            () -> EdtExecutor.runOnEdtAndWait(() -> { throw new IllegalStateException("boom"); }));

        assertEquals("Failed executing on EDT", ex.getMessage());
    }

    @Test
    void rejectsNullAction() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> EdtExecutor.runOnEdtAndWait(null));

        assertEquals("action is required", ex.getMessage());
    }
}
