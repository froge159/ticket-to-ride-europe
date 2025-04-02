package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import engine.StartEngine;
import panels.EndPanel;
import panels.GamePanel;
import panels.RulesPanel;
import panels.StartPanel;

public class StartController {

    private StartPanel startPanel;
    private RulesPanel rulesPanel;
    private GamePanel gamePanel;
    private EndPanel endPanel;
    private StartEngine startEngine;

    public StartController(StartPanel s, RulesPanel r, GamePanel g, EndPanel e, StartEngine se) {
        startPanel = s;
        endPanel = e;
        gamePanel = g;
        rulesPanel = r;
        startEngine = se;
        initListeners();
    }

    public void initListeners() {
        startPanel.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startEngine.transitionToGamePanel();
            }
        });
    }
}
