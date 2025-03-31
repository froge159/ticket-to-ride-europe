package engine;

import javax.swing.JFrame;

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
    }

    public void transitionToGamePanel() {
        
    }
}
