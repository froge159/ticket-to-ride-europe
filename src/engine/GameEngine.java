package engine;


import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import controllers.GameController;
import models.City;
import models.Path;
import models.Player;
import models.TrainCard;

import java.awt.Color;
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
import panels.SetupPanel;
import panels.TicketPanel;
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
    private SetupPanel setupPanel;
    private GamePanel gamePanel;
    private TicketPanel ticketPanel;
    private int currentPlayer = 0;
    private GameController gc;

    public GameEngine(ButtonPanel b, DrawPanel d, HandPanel[] h, MapPanel m, PlayerPanel p, SetupPanel s, GamePanel gp, TicketPanel tp) {
        buttonPanel = b;
        drawPanel = d;
        handPanels = h;
        mapPanel = m;
        playerPanel = p;
        gamePanel = gp;
        setupPanel  = s;
        ticketPanel = tp;
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
            animatedPathCards.get(index).setCorner(new Point(c.getX(), c.getY() - Rel.Y(90) * index)); // move cards up
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

    public void deckClick()  { // deck button clicked
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

    public void ticketDeckClick(){
        setTicketState(true);
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

    public void setupTicketClick(int ind) { // if setup ticket card clicked
        boolean[] clickedArray = setupPanel.getClickedArray();
        clickedArray[ind] = !clickedArray[ind]; // change boolean value
        setupPanel.getTicketButtons()[ind].setBorder(clickedArray[ind] ? new LineBorder(Color.YELLOW, Rel.W(3), true) : null); // set border if necessary
    }
    public void ticketConfirmClick(int ind) { // if setup ticket card clicked
        boolean[] clickedArray = ticketPanel.getClickedArray();
        clickedArray[ind] = !clickedArray[ind]; // change boolean value
        ticketPanel.getTicketButtons()[ind].setBorder(clickedArray[ind] ? new LineBorder(Color.YELLOW, Rel.W(3), true) : null); // set border if necessary
    }

    public void pathClick(Path path ) {
        if (mapPanel.pathIsDisabled()) return; // if map is disabled, do nothing)
    }

    public void stationClick(){
        setStationState(true);
        handPanels[currentPlayer].setHandText("Click on a city to place your station");
    }

    public void cityClick(City city) {
        if (mapPanel.cityIsDisabled()) return;
        if (handPanels[currentPlayer].getPlayer().getStations() > 0) { // if player has stations left
            //handPanels[currentPlayer].getPlayer().setStationCity(city); // set city for station placement
            handPanels[currentPlayer].setHandText("Built station on " + city.getName() + "!");
            Timer timer = new Timer(1000, e -> {
                nextPlayer();
                setStationState(false); // disable map after placing station
            });
            timer.setRepeats(false);
            timer.start();
        }
        else {
            handPanels[currentPlayer].setHandText("You have no stations left.");
            setStationState(false); // disable map after placing station
        }
    }

    public void setupPlayerTransition() {
        int clickedCount = 0;
        for (int i = 0; i < 4; i++) { // count number of clicked cards
            if (setupPanel.getClickedArray()[i]) clickedCount++;
        }

        if (clickedCount >= 2) { // add tickets if at least 2 clicked
            for (int i = 0; i < 4; i++) {
                if (setupPanel.getClickedArray()[i]) { 
                    if (i < 3) {
                        setupPanel.getPlayer().addPathCard(setupPanel.getTicketArray()[i]);
                    }
                    else setupPanel.getPlayer().setLongPathCard(setupPanel.getLongTicket()); 
                }
            }
            handPanels[setupPanel.getPlayer().getNumber()].updatePathCards(true); // update path cards in hand panel and add listeners
        }
        else return;

        if (setupPanel.getPlayer().getNumber() == 3) { // if last player
            setSetupState(false);
            gc.initPathCardListener(currentPlayer, true);
        }
        else setupPanel.updateForNextPlayer(handPanels[setupPanel.getPlayer().getNumber() + 1].getPlayer()); // switch to next player if not last
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

    public void setTicketState(boolean state) {
        //mapPanel.setPathDisabled(state);
        //mapPanel.setCityDisabled(state);
        playerPanel.setVisible(!state);
        buttonPanel.setVisible(!state);
        drawPanel.setVisible(!state);
        ticketPanel.setVisible(true);
        ticketPanel.updateForNextPlayer(handPanels[currentPlayer].getPlayer()); // update ticket panel for current player
    }

    public void setDrawCardState(boolean state) {
        buttonPanel.setEnabled(!state);
        handPanels[currentPlayer].setEnabled(!state);
        mapPanel.setPathDisabled(state);
        mapPanel.setCityDisabled(state);
    }

    public void setSetupState(boolean state) {
        mapPanel.setPathDisabled(state);
        mapPanel.setCityDisabled(state);
        handPanels[0].setVisible(!state);
        playerPanel.setVisible(!state);
        buttonPanel.setVisible(!state);
        drawPanel.setVisible(!state);
        if (!state) {
            SwingUtilities.invokeLater(() -> {
                gamePanel.remove(setupPanel);
                gamePanel.revalidate();
                gamePanel.repaint();
            });
        }
    }

    public void setStationState(boolean state) {
        handPanels[currentPlayer].setEnabled(!state);
        buttonPanel.setEnabled(!state);
        mapPanel.setPathDisabled(!state);
        mapPanel.setCityDisabled(!state);
    }

    public void ticketConfirmClick() { // ticket button clicked
        int clickedCount = 0;
        for (int i = 0; i < 3; i++) { // count number of clicked cards
            if (ticketPanel.getClickedArray()[i]) clickedCount++;
        }

        if (clickedCount >= 1) { // add tickets if at least 2 clicked
            for (int i = 0; i < 3; i++) {
                if (ticketPanel.getClickedArray()[i]) { 
                    ticketPanel.getPlayer().addPathCard(ticketPanel.getTicketArray()[i]);
                }
            }
            handPanels[ticketPanel.getPlayer().getNumber()].updatePathCards(false); // update path cards in hand panel and add listeners
        }
        else return;

        mapPanel.setEnabled(true);
        playerPanel.setVisible(true);
        buttonPanel.setVisible(true);
        drawPanel.setVisible(true);
        ticketPanel.setVisible(false);
        SwingUtilities.invokeLater(() -> {
            gamePanel.revalidate();
            gamePanel.repaint();
        });
        nextPlayer();
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
        
        gc.initPathCardListener(currentPlayer, false);
    }

    public void setGameController(GameController gc) {
        this.gc = gc;
    }   

}
