package stw;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class BasicSwingRenderer implements SwingRenderer {
    private final ComponentIdProvider idProvider;

    public BasicSwingRenderer(ComponentIdProvider idProvider) {
        if (idProvider == null) {
            throw new IllegalArgumentException("idProvider is required");
        }
        this.idProvider = idProvider;
    }

    @Override
    public RenderedComponent render(Component component) {
        if (component == null) {
            throw new IllegalArgumentException("component is required");
        }
        String componentId = idProvider.componentId(component);
        if (component instanceof JButton button) {
            return new RenderedComponent(
                "button",
                Map.of("id", componentId, "type", "button"),
                List.of(),
                button.getText()
            );
        }
        if (component instanceof JLabel label) {
            return new RenderedComponent(
                "span",
                Map.of("id", componentId, "role", "label"),
                List.of(),
                label.getText()
            );
        }
        if (component instanceof JTextField textField) {
            return new RenderedComponent(
                "input",
                Map.of(
                    "id", componentId,
                    "type", "text",
                    "value", textField.getText() == null ? "" : textField.getText()
                ),
                List.of(),
                ""
            );
        }
        if (component instanceof JPanel panel) {
            List<RenderedComponent> children = new ArrayList<>();
            for (Component child : panel.getComponents()) {
                children.add(render(child));
            }
            return new RenderedComponent(
                "div",
                Map.of("id", componentId, "role", "panel"),
                children,
                panel.getName() == null ? "" : panel.getName()
            );
        }
        return new RenderedComponent(
            "div",
            Map.of("id", componentId, "data-unsupported", component.getClass().getSimpleName()),
            List.of(),
            ""
        );
    }
}
