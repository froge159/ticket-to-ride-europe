package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import engine.GameEngine;
import engine.StartEngine;
import models.City;
import models.Path;
import panels.ButtonPanel;
import panels.DrawPanel;
import panels.GamePanel;
import panels.HandPanel;
import panels.MapPanel;
import panels.PlayerPanel;
import panels.SetupPanel;
import panels.TicketPanel;

public class GameController {
    private ButtonPanel buttonPanel;
    private DrawPanel drawPanel;
    private HandPanel[] handPanels;
    private MapPanel mapPanel;
    private PlayerPanel playerPanel;
    private GamePanel gamePanel;
    private StartEngine startEngine;
    private GameEngine ge;
    private SetupPanel setupPanel;
    private TicketPanel ticketPanel;

    public GameController(ButtonPanel b, DrawPanel d, HandPanel[] h, MapPanel m, PlayerPanel p, SetupPanel s, GamePanel gp, StartEngine se, GameEngine ge, TicketPanel tp) {
        buttonPanel = b;
        drawPanel = d;
        handPanels = h;
        mapPanel = m;
        playerPanel = p;
        gamePanel = gp;
        startEngine = se;
        setupPanel = s;
        ticketPanel = tp;
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
        
        mapPanel.addMouseListener(new MouseAdapter() { // test for clicking on path blocks and cities
            @Override
            public void mouseClicked(MouseEvent e) {
                LinkedList<Path> x = mapPanel.getMap().getPaths();
                for (int i = 0; i < x.size(); i++) {
                    Path path = x.get(i);
                    for (int j = 0; j < path.getPath().length; j++) {
                        if (path.getPath()[j].contains(e.getPoint())) {
                            System.out.println("clicked a path block");
                            ge.pathClick(path);
                        }
                    }
                }
                ArrayList<City> cities = mapPanel.getMap().getCities();
                for (int i = 0; i < cities.size(); i++) {
                    if (cities.get(i).contains(e.getPoint())) {
                        System.out.println("clicked a " + cities.get(i).getName() + " city");
                        ge.cityClick(cities.get(i));
                    }
                }
            }
        });


        drawPanel.getDeckButton().addActionListener(new ActionListener() {  // deck button clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                ge.deckClick();
            }
        });

        drawPanel.getTicketButton().addActionListener(new ActionListener() {  // deck button clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                ge.ticketDeckClick();
            }
        });

        drawPanel.getTicketButton().addActionListener(new ActionListener() {  // deck button clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                ge.ticketDeckClick();
            }
        });

        for (int i = 0; i < drawPanel.getFaceUpButtons().length; i++) { // face up draw clicked
            final int ind = i;
            drawPanel.getFaceUpButtons()[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ge.faceUpClick(ind);
                }
            });
        }

        for (int i = 0; i < setupPanel.getTicketButtons().length; i++) { // setup ticket clicked
            final int ind = i;
            setupPanel.getTicketButtons()[ind].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ge.setupTicketClick(ind);
                }
            });
        }

        for (int i = 0; i < ticketPanel.getTicketButtons().length; i++) {
            final int ind = i;
            ticketPanel.getTicketButtons()[ind].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ge.ticketClick(ind);
                    System.out.println("Ticket button " + ind + " clicked");
                }
            });
        }

        setupPanel.getConfirmButton().addActionListener(new ActionListener() { // transitiont to next setup player
            @Override
            public void actionPerformed(ActionEvent e) {
                ge.setupPlayerTransition(); 
            }
        });
        
        ticketPanel.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ge.ticketClick();
            }
        });

        buttonPanel.getStationButton().addActionListener(new ActionListener() { // station button clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                ge.stationClick();
            }
        });
    }

    public void initPathCardListeners() {
        for (int i = 0; i < handPanels.length; i++) {
            final int index = i;
            if (handPanels[index].getPlayer().getPathCards().size() > 0) {
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
            
        }
    }

    

}
          