package stw;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class DemoSwingApp {
    private DemoSwingApp() {
    }

    public static JPanel createRootPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setName("demo-panel");

        JLabel label = new JLabel("Welcome to the Swing-to-Web demo");
        label.setName("demo-label");

        JTextField textField = new JTextField("Type here");
        textField.setName("demo-input");

        JButton button = new JButton("Submit");
        button.setName("demo-button");

        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }
}
