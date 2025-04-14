import panels.StartPanel;
import panels.RulesPanel;
import panels.GamePanel;
import panels.EndPanel;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controllers.StartController;
import engine.StartEngine;
import utils.Dimensions;

public class TTRFrame extends JFrame {

    private StartPanel startPanel;
    private RulesPanel rulesPanel;
    private GamePanel gamePanel;
    private EndPanel endPanel;

    public TTRFrame() throws IOException {
        setTitle("Ticket To Ride: Europe");
        setSize(Dimensions.WIDTH, Dimensions.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        startPanel = new StartPanel();
        rulesPanel = new RulesPanel();
        gamePanel = new GamePanel();
        endPanel = new EndPanel();

        SwingUtilities.invokeLater(() -> {
            add(startPanel);
        });

        StartEngine startEngine = new StartEngine(startPanel, rulesPanel, gamePanel, endPanel, this);
        StartController startController = new StartController(startPanel, rulesPanel, gamePanel, endPanel, startEngine);

        gamePanel.setStartEngine(startEngine);
        gamePanel.initComponents();
    }
}
