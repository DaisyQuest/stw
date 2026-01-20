package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.junit.jupiter.api.Test;

class DemoSwingAppTest {
    @Test
    void buildsDemoPanelWithExpectedComponents() {
        JPanel panel = DemoSwingApp.createRootPanel();

        assertEquals("demo-panel", panel.getName());
        assertTrue(panel.getLayout() instanceof BorderLayout);

        JLabel label = (JLabel) ((BorderLayout) panel.getLayout()).getLayoutComponent(panel, BorderLayout.NORTH);
        JTextField field = (JTextField) ((BorderLayout) panel.getLayout()).getLayoutComponent(panel, BorderLayout.CENTER);
        JButton button = (JButton) ((BorderLayout) panel.getLayout()).getLayoutComponent(panel, BorderLayout.SOUTH);

        assertNotNull(label);
        assertNotNull(field);
        assertNotNull(button);
        assertEquals("demo-label", label.getName());
        assertEquals("demo-input", field.getName());
        assertEquals("demo-button", button.getName());
    }
}
