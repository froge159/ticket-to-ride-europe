package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import engine.GameEngine;
import engine.StartEngine;
import panels.ButtonPanel;
import panels.DrawPanel;
import panels.GamePanel;
import panels.HandPanel;
import panels.MapPanel;
import panels.PlayerPanel;

public class GameController {
    private ButtonPanel buttonPanel;
    private DrawPanel drawPanel;
    private HandPanel handPanel;
    private MapPanel mapPanel;
    private PlayerPanel playerPanel;
    private GamePanel gamePanel;
    private StartEngine startEngine;
    private GameEngine gameEngine;

    public GameController(ButtonPanel b, DrawPanel d, HandPanel h, MapPanel m, PlayerPanel p, GamePanel gp, StartEngine se, GameEngine ge) {
        buttonPanel = b;
        drawPanel = d;
        handPanel = h;
        mapPanel = m;
        playerPanel = p;
        gamePanel = gp;
        startEngine = se;
        gameEngine = ge;
        initListeners();
    }

    private void initListeners() {
        buttonPanel.getInfoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startEngine.transitionToRulesPanel(gamePanel);
            }
        });
    }

}
