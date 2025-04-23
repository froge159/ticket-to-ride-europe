package controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

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
    private GameEngine ge;

    public GameController(ButtonPanel b, DrawPanel d, HandPanel[] h, MapPanel m, PlayerPanel p, GamePanel gp, StartEngine se, GameEngine ge) {
        buttonPanel = b;
        drawPanel = d;
        handPanels = h;
        mapPanel = m;
        playerPanel = p;
        gamePanel = gp;
        startEngine = se;
        this.ge = ge;
        initListeners();
    }

    private void initListeners() {
        buttonPanel.getInfoButton().addActionListener(new ActionListener() { // info button clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                startEngine.setRulesPanelOrigin(gamePanel);
                startEngine.transitionToRulesPanel(gamePanel);
            }
        });
        
        mapPanel.addMouseListener(new MouseAdapter() { // testing for map clicks
            @Override
            public void mouseClicked(MouseEvent e) {
                ge.handleMouseClick(e);
            }
        });

        for (int i = 0; i < handPanels.length; i++) {
            final int index = i;
            handPanels[index].getAnimatedPathCards().get(0).addMouseListener(new MouseAdapter() { // hover event for animated path cards
                @Override
                public void mouseEntered(MouseEvent e) {
                    ge.animatePathCardsEnter(handPanels[index]);
                    System.out.println("mouse entered");
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ge.animatePathCardsLeave(handPanels[index]);
                    System.out.println("mouse exited");
                }
            });
        }

        drawPanel.getDeckButton().addActionListener(new ActionListener() { // deck button clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                ge.deckClick();
            }
        });
    }

    

}
