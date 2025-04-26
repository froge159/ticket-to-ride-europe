package engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import models.Player;
import models.TrainCard;

import java.awt.Point;



import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import panels.ButtonPanel;
import panels.DrawPanel;
import panels.GamePanel;
import panels.HandPanel;
import panels.MapPanel;
import panels.PlayerPanel;
import utils.PNGEnum;
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
        ArrayList<TrainCard> deck = drawPanel.getTrainDeck();
        HandPanel currPlayer = handPanels[currentPlayer];   
        
        TrainCard drawnCard = deck.remove(deck.size() - 1);  // remove drawn card
        currPlayer.addTrainCard(drawnCard);  // add drawn card to player inventory
        currPlayer.setHandText("Player " + (currentPlayer + 1) + " drew a " + drawnCard.getType() + " card.");
        currPlayer.updateTrainCardCounts();

        if (drawPanel.getTrainDeck().size() < 1) { // if deck is empty, refill it
            refillDrawDeck(deck, currPlayer);
        }

        drawStateTransition(currPlayer);
    }

    public void faceUpClick(int index) {
        setDrawCardState(true);
        ArrayList<TrainCard> deck = drawPanel.getTrainDeck();
        Player p = playerPanel.getPlayerArray()[currentPlayer];
        HandPanel hp = handPanels[currentPlayer];
        TrainCard[] faceUpDeck = drawPanel.getFaceUpDeck();

        TrainCard drawnCard = faceUpDeck[index];
        int wildCount = 0;

        p.addTrainCard(drawnCard);
        hp.updateTrainCardCounts();
        hp.setHandText("Player " + (currentPlayer + 1) + " drew a " + drawnCard.getType() + " card."); // add card to player inventory

        faceUpDeck[index] = null;
        for (int i = 0; i < 5; i++) { // refill face up cards
            if (deck.size() > 0) {
                if (faceUpDeck[i] == null) {
                    faceUpDeck[i] = deck.remove(deck.size() - 1);
                    drawPanel.updateFaceUpButton(i, false);
                }
            }
            else drawPanel.updateFaceUpButton(index, true);
            if (faceUpDeck[i].getType().equals("wild")) wildCount++; // increment count for wild cards
        }
        if (deck.size() < 1) { // if deck is empty, refill it
            refillDrawDeck(deck, hp);
        }
        if (wildCount >= 3) { // if at least 3 wild cards shown
            Timer timer = new Timer(1000, e -> {
                for (int i = 0; i < 5; i++) {
                    drawPanel.getDiscard().add(faceUpDeck[i]);
                    faceUpDeck[i] = null; 
                }
                refillDrawDeck(deck, hp);
                for (int i = 0; i < 5; i++) { // refill face up cards
                    if (deck.size() > 0) {
                        faceUpDeck[i] = deck.remove(deck.size() - 1);
                        drawPanel.updateFaceUpButton(i, false);
                    }
                    else drawPanel.updateFaceUpButton(index, true);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }

        if (drawnCard.getType().equals("wild")) { // if drew wild card, transition to next player
            p.setDrawn(true);
        }
        drawStateTransition(hp);
    }
   

    public void drawStateTransition(HandPanel p) { // transitions to next player or second draw 
        if (p.getPlayer().getDrawn()) {
            Timer timer = new Timer(1000, e -> {
                p.getPlayer().setDrawn(false);
                setDrawCardState(false);
                nextPlayer();
            });
            timer.setRepeats(false);
            timer.start();
        }
        else {
            p.getPlayer().setDrawn(true);
            p.setHandText(p.getHandText() + " Please draw again.");
        }
    }

    public void refillDrawDeck(ArrayList<TrainCard> deck, HandPanel hp) {
        Timer timer = new Timer(1000, e -> {
            for (int i = 0; i < drawPanel.getDiscard().size(); i++) {
                deck.add(drawPanel.getDiscard().get(i));
                drawPanel.getDiscard().remove(i); 
            }
            Collections.shuffle(drawPanel.getTrainDeck()); // shuffle deck
            hp.setHandText(hp.getHandText() + " Discard reshuffled into Deck.");
            if (drawPanel.getTrainDeck().size() < 1) { // if deck empty after refilling faceup cards
                drawPanel.getDeckButton().setVisible(false);
            }
            else drawPanel.getDeckButton().setVisible(true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void setDrawCardState(boolean state) {
        buttonPanel.setEnabled(!state);
        handPanels[currentPlayer].setEnabled(!state);
        // disable mapPanel
    }

    public void nextPlayer() {
        handPanels[currentPlayer].setVisible(false);
        handPanels[currentPlayer].setHandText("");

        if (currentPlayer == 3) currentPlayer = 0;
        else currentPlayer++;
        playerPanel.setNextPlayer(currentPlayer);

        if (!Arrays.asList(gamePanel.getComponents()).contains(handPanels[currentPlayer])) {
            gamePanel.add(handPanels[currentPlayer]);
            gamePanel.setComponentZOrder(handPanels[currentPlayer], 0);
        }
        handPanels[currentPlayer].setVisible(true);
    }

}
