package engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import models.TrainCard;

import java.awt.Point;



import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import panels.ButtonPanel;
import panels.DrawPanel;
import panels.GamePanel;
import panels.HandPanel;
import panels.MapPanel;
import panels.PlayerPanel;
import utils.Rel;
import panels.AnimatedCard;

// IMPORTANT: should be accessing attributes inside panels only.
// gamePanel is only for initializion. its attributes are passed down into the subpanels. use those attributes instead of gamePanel's attributes


public class GameEngine {

    private ButtonPanel buttonPanel;
    private DrawPanel drawPanel;
    private HandPanel[] handPanels;
    private MapPanel mapPanel;
    private PlayerPanel playerPanel;
    private GamePanel gamePanel;
    private GameEngine engine;
    private int currentPlayer = 0;

    public GameEngine(ButtonPanel b, DrawPanel d, HandPanel[] h, MapPanel m, PlayerPanel p, GamePanel gp) {
        buttonPanel = b;
        drawPanel = d;
        handPanels = h;
        mapPanel = m;
        playerPanel = p;
        gamePanel = gp;
    }

    
    public void handleMouseClick(MouseEvent e) { // print coordinates of mouse click for map (testing)
        int x = e.getX();
        int y = e.getY();
        System.out.println("\n" + x + " " + y);

        // Check if the click is within the bounds of a station
        // Example: if (x >= stationX && x <= stationX + stationWidth && y >= stationY && y <= stationY + stationHeight) {
        //     // Handle station click
        // }
    }

    public void animatePathCardsEnter(HandPanel hp) {
        ArrayList<AnimatedCard> animatedPathCards = hp.getAnimatedPathCards();
        for (int i = 1; i < animatedPathCards.size(); i++) {
            final int index = i; 
            AnimatedCard c = animatedPathCards.get(index);
            animatedPathCards.get(index).setCorner(new Point(c.getX(), c.getY() + Rel.Y(40) * index - index * Rel.Y(130))); // move cards up
            SwingUtilities.invokeLater(() -> {
                animatedPathCards.get(index).repaint();
                hp.repaint(); hp.revalidate();
            });
            if (!java.util.Arrays.asList(hp.getComponents()).contains(c)) { // if not already in the panel, add it
                SwingUtilities.invokeLater(() -> {
                    hp.add(c);
                    hp.repaint(); hp.revalidate();
                });
            }
        }
    }

    public void animatePathCardsLeave(HandPanel hp) {
        ArrayList<AnimatedCard> animatedPathCards = hp.getAnimatedPathCards();
        for (int i = 2; i < animatedPathCards.size(); i++) { // remove all cards except first two
            AnimatedCard c = animatedPathCards.get(i);
            c.setCorner(new Point(c.getX(), c.getY() - Rel.Y(40) * i + i * Rel.Y(130))); // move cards down
            if (java.util.Arrays.asList(hp.getComponents()).contains(c)) {
                SwingUtilities.invokeLater(() -> {
                    hp.remove(c);
                    hp.repaint();
                    hp.revalidate();
                });
            }
        }
        animatedPathCards.get(1).setCorner(new Point(Rel.X(17), Rel.Y(885))); // update position if 2nd card
        SwingUtilities.invokeLater(() -> {
            animatedPathCards.get(1).repaint();
            hp.repaint(); hp.revalidate();
        });
    }

    public void deckClick() throws InterruptedException { // deck button clicked
        setDrawCardState(true);
        TrainCard drawnCard = drawPanel.getTrainDeck().getLast();
        drawPanel.getTrainDeck().removeLast(); // remove card from deck

        handPanels[currentPlayer].addTrainCard(drawnCard); 
        drawPanel.showDrawnCard(drawnCard.getScaledFront(Rel.W(200), Rel.H(125))); // show drawn card on deck button

        new Timer(500, t -> {
            if (drawPanel.getTrainDeck().size() < 1) { // if deck is empty, refill it
                System.out.println("deck empty");
                drawPanel.setDeckDisabled(true);
                for (int i = 0; i < drawPanel.getDiscard().size(); i++) {
                    drawPanel.getTrainDeck().add(drawPanel.getDiscard().get(i)); 
                    drawPanel.getDiscard().remove(i); 
                }
                Collections.shuffle(drawPanel.getTrainDeck()); // shuffle deck
            }
        }).start();

        new Timer(500, t2 -> {
            if (drawPanel.getTrainDeck().size() > 0) { // if deck is not empty, show the top card
                drawPanel.setDeckDisabled(false);
            } 
        }).start();
        
    }



    public void setDrawCardState(boolean state) {
        buttonPanel.setEnabled(!state);
        handPanels[currentPlayer].setEnabled(!state);
        // disable mapPanel
    }

}
