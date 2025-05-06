package engine;


import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import controllers.GameController;
import models.City;
import models.Path;
import models.PathBlock;
import models.Player;
import models.TTRMap;
import models.TrainCard;

import java.awt.Color;
import java.awt.Point;



import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import panels.ButtonPanel;
import panels.DrawPanel;
import panels.EndPanel;
import panels.GamePanel;
import panels.HandPanel;
import panels.MapPanel;
import panels.PlayerPanel;
import panels.SetupPanel;
import panels.TicketPanel;
import panels.TunnelPanel;
import utils.ColorEnum;
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
    private TunnelPanel tunnelPanel;

    public GameEngine(ButtonPanel b, DrawPanel d, HandPanel[] h, MapPanel m, PlayerPanel p, SetupPanel s, GamePanel gp, TicketPanel tp, TunnelPanel tup) {
        buttonPanel = b;
        drawPanel = d;
        handPanels = h;
        mapPanel = m;
        playerPanel = p;
        gamePanel = gp;
        setupPanel  = s;
        ticketPanel = tp;
        tunnelPanel = tup;
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
        System.out.println("mouse entered");
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
        System.out.println("mouse left");
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
        if (ticketPanel.getNormalPaths().size() < 1) { // if no more ticket cards, do nothing
            handPanels[currentPlayer].setHandText("No more ticket cards left.");
            return;
        }
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

    public void pathClick(Path path) {
        if (gamePanel.isStationChoose() && gamePanel.getCurrentCity() != null) {
            City c = gamePanel.getCurrentCity();
            if ((path.getCity1().equals(c) || path.getCity2().equals(c)) && path.getBuyer() != null && !path.getBuyer().equals(c.getOwner())) {
                handPanels[currentPlayer].setHandText("Successfully selected path");
                c.getOwner().claimRoute(path, true); // possible bug
                Timer timer = new Timer(1000, e -> {
                    nextCity();
                });
                timer.start();
                timer.setRepeats(false);
                return;
            }
            else {
                handPanels[currentPlayer].setHandText("Please select a path that is adjacent to your station and owned by another player.");
                return;
            }
        }




        if (mapPanel.pathIsDisabled()) return; // if map is disabled, do nothing
        if (path.isBought()) { // if path is already bought, do nothing
            handPanels[currentPlayer].setHandText("Path already bought.");
            return;
        }
        TTRMap map = mapPanel.getMap(); 
        Player p = handPanels[currentPlayer].getPlayer();
        if (map.getParallelPath(path) != null && map.getParallelPath(path).getBuyer()!= null && map.getParallelPath(path).getBuyer().equals(p)) { // if path is parallel to another path that is already bought, do nothing
            handPanels[currentPlayer].setHandText("Cannot buy both parallel paths.");
            return;
        }
        if (p.getTrains() < path.getLength()) { // if player does not have enough trains, do nothing
            handPanels[currentPlayer].setHandText("Not enough trains to claim path.");
            return;
        }

        Map<String, Integer> trainCards = new HashMap<>(handPanels[currentPlayer].getPlayer().getTrainCards()); // check if player can claim path
        PathBlock[] pathBlocks = path.getPath(); 
        String maxKey = null;
        if (pathBlocks[0].getColor().equals(Color.GRAY)) { // if path is gray, find the color with the most cards
            int maxValue = 0;
            for (Map.Entry<String, Integer> entry : trainCards.entrySet()) {
                if (!entry.getKey().equals("wild") && entry.getValue() > maxValue) {
                    maxKey = entry.getKey();
                    maxValue = entry.getValue();
                }
            }
        }
        for (int i = 0; i < pathBlocks.length; i++) {
            boolean claimed = false;
            if (!pathBlocks[i].getType().equals("ferry")) {
                for (String key: trainCards.keySet()) {
                    if ((pathBlocks[i].getColor().equals(Color.GRAY) && key.equals(maxKey) || pathBlocks[i].getColor().equals(ColorEnum.getColor(key)))&& trainCards.get(key) > 0) { // if matches color and selected cards > 0
                        trainCards.put(key, trainCards.get(key) - 1); // decrement selected cards
                        claimed = true;
                    }
                }
            }
            if ((!claimed || pathBlocks[i].getType().equals("ferry")) && trainCards.get("wild") > 0) { // if wild cards available or ferry pathblock
                trainCards.put("wild", trainCards.get("wild") - 1); // decrement selected cards
                claimed = true;
            }
            if (!claimed) { // if no cards claimed, break
                handPanels[currentPlayer].setHandText("Not enough cards to claim path.");
                return;
            }
        }
    

        setUseCardState(true); // if all conditions met, set use card state to true
        p.setSelectedPath(path); // set selected path for player
        if (p.getSelectedPath().getType().equals("default") || p.getSelectedPath().getType().equals("ferry")) { //
            handPanels[currentPlayer].setHandText("Click on the train cards you want to use");
        }
        else handPanels[currentPlayer].setHandText("Click on the train cards you want to use before possible extra cards"); // if path is a tunnel or ferry, ask for wild cards
    }

    public void trainCardClick(String color){
        if (handPanels[currentPlayer].getHandText().equals("Click on the train cards you want to use to claim the station.")) {
            Player p =  handPanels[currentPlayer].getPlayer();
            int needed = p.getStations() == 3 ? 1 : p.getStations() == 2 ? 2 : 3;
            if (p.getTrainCards().get(color) < needed) { // if player does not have enough cards, do nothing
                handPanels[currentPlayer].setHandText("Not enough cards to claim station.");
                setStationState(false);
                return;
            }
            else {
                handPanels[currentPlayer].setHandText("Built station on " + p.getPendingCity().getName() + "!");
                p.getPendingCity().buildStation(handPanels[currentPlayer].getPlayer());
                p.getTrainCards().put(color, p.getTrainCards().get(color) - needed); // decrement selected cards
                handPanels[currentPlayer].updateTrainCardCounts(); // update jlabel
                playerPanel.updatePlayer(currentPlayer); // update player panel
                mapPanel.repaint();
                Timer timer = new Timer(1000, e -> {
                    nextPlayer();
                    setStationState(false); // disable map after placing station
                });
                timer.setRepeats(false);
                timer.start();
                return;
            }
        }

        if (!mapPanel.pathIsDisabled() || handPanels[currentPlayer].getJudgementState()) return; // if not in use card state, do nothing
        Player p = handPanels[currentPlayer].getPlayer();
        TreeMap<String, Integer> mp = p.getTrainCardsSelected();
        if (mp.get(color) >= p.getTrainCards().get(color) || p.getSelected() == p.getSelectedPath().getLength()) return; // if count exceeds available cards or click too much, do nothing
        mp.put(color, mp.get(color) + 1); // increment selected card count
        handPanels[currentPlayer].updateSelectedCounts(color); // update jlabel
        p.setSelected(p.getSelected() + 1); // increment selected count
    }

    public void cancelClick() {
        Player p = handPanels[currentPlayer].getPlayer();
        for (String key : p.getTrainCardsSelected().keySet()) {
            p.getTrainCardsSelected().put(key, 0); // reset the count for each selected card
            handPanels[currentPlayer].updateSelectedCounts(key); // update the UI to reflect the reset
        }
        p.setSelected(0); // reset selected count
        handPanels[currentPlayer].setHandText("Selection canceled.");
    }

    public void okClick() {
        Player p = handPanels[currentPlayer].getPlayer();
        TreeMap<String, Integer> selectedMap = p.getTrainCardsSelected();

        if (p.getSelected() < p.getSelectedPath().getPath().length) { handPanels[currentPlayer].setHandText("Please choose more cards"); return; }
        
        if (selectedMap.entrySet().stream().filter(entry -> !entry.getKey().equals("wild") && entry.getValue() > 0).count() > 1 ) {
            handPanels[currentPlayer].setHandText("You cannot select more than one card of the same color.");
            for (String key : p.getTrainCardsSelected().keySet()) {
                p.getTrainCardsSelected().put(key, 0); // reset the count for each selected card
                handPanels[currentPlayer].updateSelectedCounts(key); // update the UI to reflect the reset
            }
            p.setSelected(0); // reset selected count
            return;
        }

        TreeMap<String, Integer> trainCards = p.getTrainCards();
        PathBlock[] pathBlocks = p.getSelectedPath().getPath();
        String maxKey = null;
        int maxValue = 0;
        for (int i = 0; i < pathBlocks.length; i++) {
            if (pathBlocks[i].getColor().equals(Color.GRAY)) { // if path is gray, find the color with the most cards
                for (Map.Entry<String, Integer> entry : selectedMap.entrySet()) {
                    if (!entry.getKey().equals("wild") && entry.getValue() > maxValue) {
                        maxKey = entry.getKey();
                        maxValue = entry.getValue();
                    }
                }
                break;
            }
        }

        if (maxKey == null) maxKey = "wild";
        

        System.out.println(selectedMap);
        for (int i = 0; i < pathBlocks.length; i++) {
            boolean claimed = false;
            if (!pathBlocks[i].getType().equals("ferry")) {
                for (String key: selectedMap.keySet()) {
                    if ((pathBlocks[i].getColor().equals(Color.GRAY) && key.equals(maxKey) || pathBlocks[i].getColor().equals(ColorEnum.getColor(key))) && selectedMap.get(key) > 0) { // if matches color and selected cards > 0
                        if (!pathBlocks[0].getType().equals("mountain")) selectedMap.put(key, selectedMap.get(key) - 1); 
                        claimed = true;
                        break;
                    }
                }
            }
            if ((!claimed || pathBlocks[i].getType().equals("ferry")) && trainCards.get("wild") > 0) { // if wild cards available or ferry pathblock
                if (!pathBlocks[0].getType().equals("mountain")) trainCards.put("wild", trainCards.get("wild") - 1); 
                claimed = true;
            }
            else if (!claimed) {
                handPanels[currentPlayer].setHandText("Invalid Card Selection");

                for (String key : p.getTrainCardsSelected().keySet()) {
                    p.getTrainCardsSelected().put(key, 0); // reset the count for each selected card
                    handPanels[currentPlayer].updateSelectedCounts(key); // update the UI to reflect the reset
                }
                p.setSelected(0); // reset selected count
                
                return;
            }
        }

        if (!pathBlocks[0].getType().equals("mountain")) {
            p.claimRoute(p.getSelectedPath(), false); // claim route
            p.getSelectedPath().buy(p);
            p.setSelectedPath(null); // reset selected path
            p.setSelected(0);
            playerPanel.updatePlayer(currentPlayer); // update player panel
            handPanels[currentPlayer].updateTrainCardCounts();
            handPanels[currentPlayer].setHandText("Path claimed!"); 
            Timer timer = new Timer (1000, e -> {
                setUseCardState(false); // disable use card state
                nextPlayer(); //
            });
            timer.setRepeats(false);
            timer.start(); 
            mapPanel.repaint();
        }

        else if (handPanels[currentPlayer].isPostJudgement()) {
            int needed = p.getExtraCardsNeeded(); 
            ArrayList<TrainCard> lastThreeCards = p.getLastThreeCards(); // get last three cards drawn
            System.out.println("Needed: " + needed + " Needed cards " + lastThreeCards);
            for (int i = 0; i < lastThreeCards.size(); i++) {
                for (String key: selectedMap.keySet()) {
                    if ((lastThreeCards.get(i).getType().equals(key) || key.equals("wild")) && selectedMap.get(key) > 0) { // if color matches or wild card drawn, decrement needed
                        needed--;
                        break;
                    }
                }
            }
            if (needed > 0) {
                handPanels[currentPlayer].setHandText("You did not select enough cards to claim the path.");
            }
            else {
                p.claimRoute(p.getSelectedPath(), false); // claim route
                p.getSelectedPath().buy(p);
                p.setSelectedPath(null); // reset selected path
                p.setSelected(0);
                p.setExtraCardsNeeded(0);
                p.setLastThreeCards(null);
                handPanels[currentPlayer].setPostJudgement(false);
                playerPanel.updatePlayer(currentPlayer); // update player panel
                handPanels[currentPlayer].updateTrainCardCounts();
                handPanels[currentPlayer].setHandText("Path claimed!"); 
                Timer timer = new Timer (1000, e -> {
                    setUseCardState(false); // disable use card state
                    nextPlayer(); //
                });
                timer.setRepeats(false);
                timer.start(); 
                mapPanel.repaint();
            }
            
        }

        else {
            ArrayList<TrainCard> deck = drawPanel.getTrainDeck();
            if (deck.size() < 3) {
                refillDrawDeck(deck, handPanels[currentPlayer]);
            }
            if (deck.size() == 0) { // if deck is empty, do nothing
                p.claimRoute(p.getSelectedPath(), false); // claim route
                p.getSelectedPath().buy(p);
                p.setSelectedPath(null); // reset selected path
                p.setSelected(0);
                p.resetTrainCardsSelected();
                playerPanel.updatePlayer(currentPlayer); // update player panel
                handPanels[currentPlayer].updateTrainCardCounts();
                handPanels[currentPlayer].resetSelectedCounts();
                handPanels[currentPlayer].setHandText("Path claimed. Draw deck was empty"); 
                Timer timer = new Timer (1000, e -> {
                    setUseCardState(false); // disable use card state
                    nextPlayer(); //
                });
                timer.setRepeats(false);
                timer.start(); 
                mapPanel.repaint();
            }
            else {
                String color = ""; int needed = 0;
                maxValue = 0;
                for (Map.Entry<String, Integer> entry : selectedMap.entrySet()) { // calculate color
                    if (entry.getKey().equals("wild")) continue;
                    if (entry.getValue() > maxValue) {
                        color = entry.getKey();
                        maxValue = entry.getValue();
                    }
                }

                if (color.equals("") && selectedMap.get("wild") > 0) {
                    color = "wild";
                    maxValue = selectedMap.get("wild");
                }

                else if (!color.equals("wild") && selectedMap.get("wild") > selectedMap.get(color)) {
                    color = "wild";
                    maxValue = selectedMap.get("wild");
                }
                
                ArrayList<TrainCard> lastThreeCards = new ArrayList<>(); // calculate needed cards
                ArrayList<TrainCard> neededCards = new ArrayList<>();
                int cardsToTake = Math.min(3, deck.size());
                for (int i = 0; i < cardsToTake; i++) {
                    lastThreeCards.add(deck.remove(deck.size() - 1));
                }
                for (int i = 0; i < lastThreeCards.size(); i++) {
                    if (color.equals("wild") && lastThreeCards.get(i).getType().equals("wild")) { // if wild card drawn, do nothing
                        needed++;
                        neededCards.add(lastThreeCards.get(i));
                    }
                    else if (!color.equals("wild") && (lastThreeCards.get(i).getType().equals(color) || lastThreeCards.get(i).getType().equals("wild"))) { // if color matches or wild card drawn, do nothing
                        needed++;
                        neededCards.add(lastThreeCards.get(i));
                    }
                }
                TreeMap<String, Integer> differenceMap = new TreeMap<>();
                for (Map.Entry<String, Integer> entry : selectedMap.entrySet()) {
                    String key = entry.getKey();
                    int selectedValue = entry.getValue();
                    int trainCardValue = trainCards.get(key);
                    differenceMap.put(key, trainCardValue - selectedValue);
                }

                
                System.out.println(differenceMap);
                int neededCopy = needed;
                // if equals color or wild, needed--
                for (int i = 0; i < neededCards.size(); i++) {
                    for (String key: differenceMap.keySet()) {
                        if ((neededCards.get(i).getType().equals("wild") || neededCards.get(i).getType().equals(color)) && (neededCards.get(i).getType().equals(key) || key.equals("wild")) && differenceMap.get(key) > 0) { // if color matches or wild card drawn, decrement needed
                            neededCopy--;
                            differenceMap.put(key, differenceMap.get(key) - 1);
                            break;
                        }
                    }
                } 
                System.out.println(differenceMap);
                if (neededCopy > 0) {
                    setTunnelState(true);
                    tunnelPanel.setText("You do not have enough cards to claim the path.");
                    p.setExtraCardsNeeded(needed);
                }
                else {
                    setTunnelState(true);
                    tunnelPanel.setText("You need to pay " + Math.max(needed, 0) + " more " + color + " cards to claim the path.");
                    for (String key: selectedMap.keySet()) {
                        trainCards.put(key, trainCards.get(key) - selectedMap.get(key)); // decrement selected cards
                    }
                    handPanels[currentPlayer].updateTrainCardCounts(); // update jlabel
                    p.setExtraCardsNeeded(needed);
                    p.setLastThreeCards(neededCards);
                }
                tunnelPanel.updatePanel(lastThreeCards);
                for (int i = 0; i < lastThreeCards.size(); i++) {
                    drawPanel.getDiscard().add(lastThreeCards.get(i)); // add drawn cards to discard pile
                }

                p.resetTrainCardsSelected();
                handPanels[currentPlayer].resetSelectedCounts();
                p.setSelected(0); 
            }
        }
        
    }

    public void tunnelReturnClick() {
        Player p = handPanels[currentPlayer].getPlayer();
        if (p.getExtraCardsNeeded() < 1) {
            p.claimRoute(p.getSelectedPath(), false); // claim route
            p.getSelectedPath().buy(p);
            p.setSelectedPath(null); // reset selected path
            p.setSelected(0);
            p.setExtraCardsNeeded(0);
            p.setLastThreeCards(null);
            handPanels[currentPlayer].setPostJudgement(false);
            playerPanel.updatePlayer(currentPlayer); // update player panel
            handPanels[currentPlayer].updateTrainCardCounts();
            handPanels[currentPlayer].setHandText("Path claimed!"); 
            Timer timer = new Timer (1000, e -> {
                setUseCardState(false); // disable use card state
                nextPlayer(); //
            });
            timer.setRepeats(false);
            timer.start(); 
            mapPanel.repaint();

            setTunnelState(false);
        }
        else if (!tunnelPanel.isAbleToPay()) {
            setTunnelState(false);
            setUseCardState(false);
            p.setSelectedPath(null);
            nextPlayer();
        }
        else {
            setTunnelState(false);
            handPanels[currentPlayer].setPostJudgement(true); 
            handPanels[currentPlayer].setHandText(tunnelPanel.getText());
        }
    }


    public void stationClick(){
        setStationState(true);
        handPanels[currentPlayer].setHandText("Click on a city to place your station");
    }

    public void cityClick(City city) {
        if (mapPanel.cityIsDisabled()) return;
        System.out.println(mapPanel.cityIsDisabled());
        if (city.hasStation()) { // if city already has a station, do nothing
            handPanels[currentPlayer].setHandText("City already has a station.");
            setStationState(false);
            return;
        }
        if (handPanels[currentPlayer].getPlayer().getStations() > 0) { // if player has stations left
            TreeMap<String, Integer> trainCards = handPanels[currentPlayer].getPlayer().getTrainCards(); // get player train cards
            Player p = handPanels[currentPlayer].getPlayer();
            boolean canClaim = false;
            int needed = p.getStations() == 3 ? 1 : p.getStations() == 2 ? 2 : 3;
            for (String key: trainCards.keySet()) {
                if (trainCards.get(key) >= needed) { // if player has enough cards to claim station
                    canClaim = true;
                    break;
                }
            }

            if (!canClaim) { // if player does not have enough cards, do nothing
                handPanels[currentPlayer].setHandText("Not enough cards to claim station.");
                setStationState(false); // disable map after placing station
                return;
            }

            else {
                p.setPendingCity(city); // set pending city for player
                handPanels[currentPlayer].setHandText("Click on the train cards you want to use to claim the station.");
            }
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
            handPanels[setupPanel.getPlayer().getNumber()].updatePathCards(gamePanel); // update path cards in hand panel and add listeners
        }
        else return;

        if (setupPanel.getPlayer().getNumber() == 3) { // if last player
            setSetupState(false);
        }
        else setupPanel.updateForNextPlayer(handPanels[setupPanel.getPlayer().getNumber() + 1].getPlayer()); // switch to next player if not last
    }
   

    public void drawStateTransition(HandPanel p) { // transitions to next player or second draw 
        if (p.getPlayer().getDrawn()) {
            drawPanel.setDisabled(true);
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

    public void setUseCardState(boolean state) {
        mapPanel.setPathDisabled(state);
        mapPanel.setCityDisabled(state);
        buttonPanel.setEnabled(!state);
        handPanels[currentPlayer].setEnabled(state);
        handPanels[currentPlayer].showSelectedCounts(state);
        drawPanel.setAllEnabled(!state);
    }

    public void setTunnelState(boolean state) {
        tunnelPanel.setVisible(state);
        playerPanel.setVisible(!state);
        buttonPanel.setVisible(!state);
        drawPanel.setVisible(!state);
        handPanels[currentPlayer].setJudgementState(state);
        handPanels[currentPlayer].showButtons(!state);
    }

    public void setTicketState(boolean state) {
        mapPanel.setPathDisabled(state);
        mapPanel.setCityDisabled(state);
        playerPanel.setVisible(!state);
        buttonPanel.setVisible(!state);
        drawPanel.setVisible(!state);
        ticketPanel.setVisible(true);
        ticketPanel.updateForNextPlayer(handPanels[currentPlayer].getPlayer()); // update ticket panel for current player
    }

    public void setDrawCardState(boolean state) {
        if (!state) {
            drawPanel.setDisabled(false);
        }
        drawPanel.setTicketButtonEnabled(!state);
        buttonPanel.setEnabled(!state);
        mapPanel.setPathDisabled(state);
        mapPanel.setCityDisabled(state);
    }

    public void setSetupState(boolean state) {
        mapPanel.setPathDisabled(state);
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
            gc.initPathCardListeners();
        }
    }

    public void setStationState(boolean state) {
        buttonPanel.setEnabled(!state);
        mapPanel.setPathDisabled(state);
        mapPanel.setCityDisabled(!state);
        drawPanel.setDisabled(state);
    }

    public void setChooseStationState(boolean state) {
        mapPanel.setCityDisabled(state);
        drawPanel.setDisabled(state);
        buttonPanel.setEnabled(!state);

        gamePanel.setEndingGame(false);
        gamePanel.setStationChoose(true);
        gamePanel.setFinalTurnCount(0);
        if (state) {
            nextCity();
        }
    }

    public void ticketClick() { // ticket button clicked
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
            handPanels[ticketPanel.getPlayer().getNumber()].updatePathCards(gamePanel); // update path cards in hand panel and add listeners
        }
        else return;

        mapPanel.setCityDisabled(false);
        mapPanel.setPathDisabled(false);
        playerPanel.setVisible(true);
        buttonPanel.setVisible(true);
        drawPanel.setVisible(true);
        ticketPanel.setVisible(false);
        SwingUtilities.invokeLater(() -> {
            gamePanel.revalidate();
            gamePanel.repaint();
        });
        nextPlayer();
        gc.initPathCardListeners();
    }

    public void nextPlayer() {
        if (handPanels[currentPlayer].getPlayer().getTrains() <= 2 && !gamePanel.isEndingGame()) {
            Timer timer = new Timer(1000, e -> {
                handPanels[currentPlayer].setHandText("Every player has one final turn.");
            });
            timer.start();
            timer.setRepeats(false);
            gamePanel.setEndingGame(true);
            gamePanel.getEndPanelButton().setText("Skip Turn");
            gamePanel.getEndPanelButton().setVisible(true);
        }
        if (gamePanel.getFinalTurnCount() == 4) {
            gamePanel.getEndPanelButton().setText("See Scores");
            gamePanel.getEndPanelButton().setVisible(false);
            setChooseStationState(true);
            return;
        }

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

        if (gamePanel.isEndingGame()) {
            gamePanel.setFinalTurnCount(gamePanel.getFinalTurnCount() + 1);
        }
    }

    public void nextCity() {
        City city = null;
        int r = gamePanel.getStationR();
        boolean selected = false;
        System.out.println(r);
        for (int ind = r; ind < mapPanel.getCities().size(); ind++) {
            final int i = ind;
            if (mapPanel.getCities().get(i).getOwner() != null 
                && mapPanel.getCities().get(i).getPaths().stream().anyMatch(path -> path.getBuyer() != null && !path.getBuyer().equals(mapPanel.getCities().get(i).getOwner()))) {
                city = mapPanel.getCities().get(i);
                selected = true;
                gamePanel.setStationR(i + 1);
                break;
            }
        }

        if (!selected) {
            handPanels[currentPlayer].setHandText("Calculating Scores...");
            int maxLength = 0;
            Player p = null;
            for (int i = 0; i < 4; i++) {
                if (handPanels[i].getPlayer().getTrains() > maxLength) {
                    maxLength = handPanels[i].getPlayer().getLongestPathLength();
                    p = handPanels[i].getPlayer();
                }
                handPanels[i].getPlayer().calculateScore(mapPanel.getCities());
            }
            if (maxLength > 0) p.setPoints(p.getPoints() + 10);

            gc.getEndPanel().updatePanel();
            gc.getEndPanel().setVisible(true);
            
            mapPanel.setPathDisabled(true);
            mapPanel.setCityDisabled(true);
            drawPanel.setDisabled(true);
            buttonPanel.setEnabled(false);


            setGamePanelVisible(false);
        }
        else {
            handPanels[currentPlayer].setHandText("Player " + (city.getOwner().getNumber() + 1) + ", choose a path from your station at " + city.getName() + ".");
            gamePanel.setCurrentCity(city);
        }
        
    }

    public void nextEndPlayerClick() {
        handPanels[currentPlayer].setVisible(false);
        handPanels[currentPlayer].setHandText("");

        if (currentPlayer == 3) currentPlayer = 0;
        else currentPlayer++;
        playerPanel.setNextPlayer(currentPlayer);

        handPanels[currentPlayer].setVisible(true);
    }

    public void setGamePanelVisible(boolean state) {
        drawPanel.setVisible(state);
            playerPanel.setVisible(state);
            buttonPanel.setVisible(state);
            handPanels[currentPlayer].setVisible(state);
    }

    public void setGameController(GameController gc) {
        this.gc = gc;
    }   

}
