package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JButton;
import org.junit.jupiter.api.Test;

class ComponentIdProviderTest {
    @Test
    void prefersComponentName() {
        JButton button = new JButton();
        button.setName(" primary ");

        String id = new ComponentIdProvider().componentId(button);

        assertEquals("primary", id);
    }

    @Test
    void generatesStableFallbackId() {
        JButton button = new JButton();

        String id = new ComponentIdProvider().componentId(button);

        assertTrue(id.startsWith("component-"));
    }

    @Test
    void fallsBackWhenNameIsBlank() {
        JButton button = new JButton();
        button.setName("  ");

        String id = new ComponentIdProvider().componentId(button);

        assertTrue(id.startsWith("component-"));
    }
}
