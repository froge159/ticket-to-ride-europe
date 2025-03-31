package panels;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StartPanel extends JPanel {
    private JButton startButton;

    public StartPanel() {
        startButton = new JButton();

        add(startButton);
    }

    public JButton getStartButton() {
        return startButton;
    }
}
