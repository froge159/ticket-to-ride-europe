package controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.MouseEvent;

import engine.GameEngine;
import engine.StartEngine;
import models.Path;
import models.PathBlock;
import models.TTRMap;
import panels.ButtonPanel;
import panels.DrawPanel;
import panels.GamePanel;
import panels.HandPanel;
import panels.MapPanel;
import panels.PlayerPanel;

public class GameController {
    private ButtonPanel buttonPanel;
    private DrawPanel drawPanel;
    private HandPanel[] handPanels;
    private MapPanel mapPanel;
    private PlayerPanel playerPanel;
    private GamePanel gamePanel;
    private StartEngine startEngine;
    private GameEngine gameEngine;

    public GameController(ButtonPanel b, DrawPanel d, HandPanel[] h, MapPanel m, PlayerPanel p, GamePanel gp, StartEngine se, GameEngine ge) {
        buttonPanel = b;
        drawPanel = d;
        handPanels = h;
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
                startEngine.setRulesPanelOrigin(gamePanel);
                startEngine.transitionToRulesPanel(gamePanel);
            }
        });
        
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameEngine.handleMouseClick(e);
                Point clickPoint = e.getPoint();
                for (Path path : gamePanel.getMap().getPaths()) {
                    for(PathBlock rect : path.getPath()) {
                        if (rect.contains(clickPoint)) {
                            System.out.println("clicked");
                            // Add your click logic here
                        }
                    }
                }
                gamePanel.repaint();
            }
        });
    }

    

}
