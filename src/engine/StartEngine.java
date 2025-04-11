package engine;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import panels.EndPanel;
import panels.GamePanel;
import panels.RulesPanel;
import panels.StartPanel;

public class StartEngine {
    private StartPanel startPanel;
    private RulesPanel rulesPanel;
    private GamePanel gamePanel;
    private EndPanel endPanel;
    private StartEngine startEngine;
    private JFrame ttrFrame;

    public StartEngine (StartPanel s, RulesPanel r, GamePanel g, EndPanel e, JFrame tf) {
        startPanel = s;
        endPanel = e;
        gamePanel = g;
        rulesPanel = r;
        ttrFrame = tf;
    }

    public void transitionToGamePanel() {
        SwingUtilities.invokeLater(() -> {
            ttrFrame.getContentPane().remove(startPanel);
            ttrFrame.add(gamePanel);
            ttrFrame.revalidate();
        });
    }

    public void transitionToRulesPanel() {
        SwingUtilities.invokeLater(() -> {
            ttrFrame.getContentPane().remove(startPanel);
            ttrFrame.add(rulesPanel);
            ttrFrame.revalidate();
        });
    }

    public void transitionToEndPanel() {
        SwingUtilities.invokeLater(() -> {
            ttrFrame.getContentPane().remove(startPanel);
            ttrFrame.add(endPanel);
            ttrFrame.revalidate();
        });

    }
}
