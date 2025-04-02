import panels.StartPanel;
import panels.RulesPanel;
import panels.GamePanel;
import panels.EndPanel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controllers.StartController;
import engine.StartEngine;

public class TTRFrame extends JFrame {

    public static int WIDTH = 1600;
    public static int HEIGHT = 1000;

    private StartPanel startPanel;
    private RulesPanel rulesPanel;
    private GamePanel gamePanel;
    private EndPanel endPanel;

    public TTRFrame() {
        setTitle("Ticket To Ride: Europe");
        setSize(1600, 1000);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        startPanel = new StartPanel();
        rulesPanel = new RulesPanel();
        gamePanel = new GamePanel();
        endPanel = new EndPanel();

        add(startPanel);

        StartEngine startEngine = new StartEngine(startPanel, rulesPanel, gamePanel, endPanel, this);
        StartController startController = new StartController(startPanel, rulesPanel, gamePanel, endPanel, startEngine);

    }
}
