package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.junit.jupiter.api.Test;

class BasicSwingRendererTest {
    private final BasicSwingRenderer renderer = new BasicSwingRenderer(new ComponentIdProvider());

    @Test
    void rendersButton() {
        JButton button = new JButton("Click");
        button.setName("btn");

        RenderedComponent rendered = renderer.render(button);

        assertEquals("button", rendered.getTag());
        assertEquals("btn", rendered.getAttributes().get("id"));
        assertEquals("Click", rendered.getTextContent());
    }

    @Test
    void rendersLabel() {
        JLabel label = new JLabel("Label");
        label.setName("lbl");

        RenderedComponent rendered = renderer.render(label);

        assertEquals("span", rendered.getTag());
        assertEquals("Label", rendered.getTextContent());
    }

    @Test
    void rendersTextField() {
        JTextField field = new JTextField() {
            @Override
            public String getText() {
                return null;
            }
        };
        field.setName("field");

        RenderedComponent rendered = renderer.render(field);

        assertEquals("input", rendered.getTag());
        assertEquals("", rendered.getAttributes().get("value"));
    }

    @Test
    void rendersTextFieldWithValue() {
        JTextField field = new JTextField("value");
        field.setName("field");

        RenderedComponent rendered = renderer.render(field);

        assertEquals("input", rendered.getTag());
        assertEquals("value", rendered.getAttributes().get("value"));
    }

    @Test
    void rendersPanel() {
        JPanel panel = new JPanel();
        panel.setName("Panel");

        RenderedComponent rendered = renderer.render(panel);

        assertEquals("div", rendered.getTag());
        assertEquals("Panel", rendered.getTextContent());
    }

    @Test
    void rendersPanelChildren() {
        JPanel panel = new JPanel();
        panel.setName("Panel");
        panel.add(new JLabel("Child"));

        RenderedComponent rendered = renderer.render(panel);

        assertEquals(1, rendered.getChildren().size());
        assertEquals("span", rendered.getChildren().getFirst().getTag());
    }

    @Test
    void rendersPanelWithNullName() {
        JPanel panel = new JPanel();
        panel.setName(null);

        RenderedComponent rendered = renderer.render(panel);

        assertEquals("div", rendered.getTag());
        assertEquals("", rendered.getTextContent());
    }

    @Test
    void rendersUnsupportedComponent() {
        java.awt.Canvas canvas = new java.awt.Canvas();
        canvas.setName("unsupported");

        RenderedComponent rendered = renderer.render(canvas);

        assertEquals("div", rendered.getTag());
        assertEquals("Canvas", rendered.getAttributes().get("data-unsupported"));
    }

    @Test
    void rejectsNullComponent() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> renderer.render(null));

        assertEquals("component is required", ex.getMessage());
    }

    @Test
    void rejectsMissingIdProvider() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> new BasicSwingRenderer(null));

        assertEquals("idProvider is required", ex.getMessage());
    }
}
