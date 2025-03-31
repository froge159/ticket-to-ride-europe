package controllers;

import javax.swing.JPanel;

public class GameController {
    private JPanel buttonPanel, drawPanel, handPanel, mapPanel, playerPanel;

    public GameController(JPanel b, JPanel d, JPanel h, JPanel m, JPanel p) {
        buttonPanel = b; drawPanel = d; handPanel = h; mapPanel = m; playerPanel = p;
    }
    
}
