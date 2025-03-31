package engine;

import javax.swing.JPanel;

public class GameEngine {
    private JPanel buttonPanel, drawPanel, handPanel, mapPanel, playerPanel;


    public GameEngine(JPanel b, JPanel d, JPanel h, JPanel m, JPanel p) {
        buttonPanel = b; drawPanel = d; handPanel = h; mapPanel = m; playerPanel = p;
    }
    
}
