package engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

    public void setRulesPanelOrigin(JPanel panel) {
        rulesPanel.setOriginPanel(panel);
    }

    public void transitionToPanel(JPanel orig, JPanel target) {
        SwingUtilities.invokeLater(() -> {
            orig.setVisible(false);
            if (target.getParent() == null) {
                ttrFrame.add(target);
            } else {
                target.setVisible(true);
            }
            ttrFrame.revalidate();
        });
    }

    public void transitionToRulesPanel(JPanel orig) {
        transitionToPanel(orig, rulesPanel);
    }
}
