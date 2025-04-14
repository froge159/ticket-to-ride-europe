package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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

    private void initListeners() {
        startPanel.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startEngine.transitionToPanel(startPanel, gamePanel);
            }
        });

        startPanel.getInfoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startEngine.transitionToPanel(startPanel, rulesPanel);
            }
        });


        //delete this later
        JButton tempButton = new JButton("End"); 
        SwingUtilities.invokeLater(() -> {
            startPanel.add(tempButton);// Placeholder for replay button
            tempButton.setBounds(0, 0, 200, 40);
        });
        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder for end button action
                startEngine.transitionToPanel(startPanel, endPanel);
            }
        });
    }
}
