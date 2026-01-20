package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import javax.swing.JLabel;
import org.junit.jupiter.api.Test;

class SwingWebServletConfigTest {
    @Test
    void buildsWithDefaults() {
        SwingWebServletConfig config = SwingWebServletConfig.builder()
            .rootComponentSupplier(() -> new JLabel("Root"))
            .build();

        assertEquals(Duration.ofMinutes(20), config.getSessionTimeout());
        assertTrue(config.isDiffRenderingEnabled());
    }

    @Test
    void customizesOptions() {
        SwingWebServletConfig config = SwingWebServletConfig.builder()
            .rootComponentSupplier(() -> new JLabel("Root"))
            .sessionTimeout(Duration.ofMinutes(5))
            .diffRenderingEnabled(false)
            .build();

        assertEquals(Duration.ofMinutes(5), config.getSessionTimeout());
        assertFalse(config.isDiffRenderingEnabled());
    }

    @Test
    void rejectsInvalidTimeout() {
        SwingWebServletConfig.Builder builder = SwingWebServletConfig.builder()
            .rootComponentSupplier(() -> new JLabel("Root"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> builder.sessionTimeout(Duration.ZERO));

        assertEquals("sessionTimeout must be positive", ex.getMessage());
    }

    @Test
    void rejectsMissingRootSupplier() {
        NullPointerException ex = assertThrows(NullPointerException.class,
            () -> SwingWebServletConfig.builder().build());

        assertEquals("rootComponentSupplier", ex.getMessage());
    }

    @Test
    void rejectsNullTimeout() {
        SwingWebServletConfig.Builder builder = SwingWebServletConfig.builder()
            .rootComponentSupplier(() -> new JLabel("Root"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> builder.sessionTimeout(null));

        assertEquals("sessionTimeout must be positive", ex.getMessage());
    }

    @Test
    void rejectsNegativeTimeout() {
        SwingWebServletConfig.Builder builder = SwingWebServletConfig.builder()
            .rootComponentSupplier(() -> new JLabel("Root"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> builder.sessionTimeout(Duration.ofSeconds(-1)));

        assertEquals("sessionTimeout must be positive", ex.getMessage());
    }
}
