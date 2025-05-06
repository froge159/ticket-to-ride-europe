package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.PathCard;
import models.Player;
import models.TrainCard;
import utils.Rel;
import utils.CardImages;

public class HandPanel extends JPanel {
    // panel for whoever is playing rn

    private Player p;
    private String[] temp = { "black", "blue", "orange", "green", "purple", "red", "white", "yellow", "wild" };
    private ArrayList<AnimatedCard> animatedPathCards;
    private JButton okButton, cancelButton;
    private JButton[] playerTrainButtons;
    private TreeMap<String, JLabel> trainCardCounts;
    private TreeMap<String, JLabel> selectedCounts;
    private JLabel text;
    private boolean judgementState = false;
    private boolean postJudgement = false;

    public HandPanel(Player p) {
        this.p = p; 
        setOpaque(false);
        // setBorder(new javax.swing.border.LineBorder(Color.YELLOW, Rel.W(3), true));
        

        setLayout(null);
        setSize(Rel.W(1700), Rel.H(1080));
        updatePanel();
    }

    public void updatePanel() {
        SwingUtilities.invokeLater(() -> {
            removeAll();
        });
        
        TreeMap<String, Integer> mp = p.getTrainCards();
        TreeMap<String, Integer> mpSelected = p.getTrainCardsSelected();
        animatedPathCards = new ArrayList<>();
        playerTrainButtons = new JButton[temp.length];
        trainCardCounts = new TreeMap<>();
        selectedCounts = new TreeMap<>();

        

        int jlabelX = 278; 
        int cardX = 225;
        for (int i = 0; i < temp.length; i++) {
            String color = temp[i];
            
            JLabel count = new JLabel(String.valueOf(mp.get(color)));
            count.setFont(new Font("Arial", Font.PLAIN, Rel.W(30)));
            count.setForeground(Color.YELLOW);
            count.setBounds(Rel.X(jlabelX), Rel.Y(945), Rel.W(30), Rel.H(30));
            trainCardCounts.put(color, count);

            JLabel clickCount = new JLabel (String.valueOf(mpSelected.get(color)));
            clickCount.setFont(new Font("Arial", Font.BOLD, Rel.W(30)));
            clickCount.setForeground(Color.RED);
            clickCount.setBounds(Rel.X(jlabelX), Rel.Y(1000), Rel.W(25), Rel.H(25));
            selectedCounts.put(color, clickCount);  

            BufferedImage b;
            try {
                b = ImageIO.read(HandPanel.class.getResource("/assets/traincards/"+color+"rotatedcard.png"));
                JButton card = new JButton(new ImageIcon(b.getScaledInstance(Rel.W(130), Rel.H(200), java.awt.Image.SCALE_SMOOTH))); // 125 /200
                card.setBounds(Rel.X(cardX), Rel.Y(880), Rel.W(125), Rel.H(200));
                card.setOpaque(false);
                card.setContentAreaFilled(false);
                card.setBorderPainted(false);
                playerTrainButtons[i] = card;
                
                jlabelX += 140;
                cardX += 140; 
                SwingUtilities.invokeLater(() -> { 
                    add(count);
                    add(clickCount);
                    add(card);
                    setComponentZOrder(count, 0);
                    setComponentZOrder(clickCount, 1);
                    setComponentZOrder(card, 2);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            
        }

        text = new JLabel();
        text.setFont(new Font("Arial", Font.BOLD, Rel.W(15)));
        text.setForeground(Color.RED);
        text.setBounds(Rel.X(17), Rel.Y(845), Rel.W(1000), Rel.H(50));

        okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.PLAIN, Rel.W(15)));
        okButton.setBounds(Rel.X(1495), Rel.Y(910), Rel.W(80), Rel.H(40));

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, Rel.W(15)));
        cancelButton.setBounds(Rel.X(1495), Rel.Y(980), Rel.W(80), Rel.H(40));

        SwingUtilities.invokeLater(() -> {
            add(okButton);
            add(cancelButton);
            add(text);
            revalidate();
            repaint();
        });
        okButton.setVisible(false);
        cancelButton.setVisible(false);
        showSelectedCounts(false);
    }

    public void updatePathCards(GamePanel gp) {
        for (AnimatedCard animatedCard : animatedPathCards) {
            SwingUtilities.invokeLater(() -> {
                gp.remove(animatedCard);
                gp.revalidate();
                gp.repaint();
            });
        }
        animatedPathCards.clear();
        Stack<PathCard> pc = p.getPathCards();
        final int[] y = { 925 };
        int x = 0;
        Iterator<PathCard> it = pc.iterator();
        while (it.hasNext()) {
            PathCard currentCard = it.next();
            AnimatedCard animatedCard = new AnimatedCard(currentCard, Rel.W(200), Rel.H(125), Rel.X(17), Rel.Y(y[0]));
            animatedPathCards.add(animatedCard);

            if (x < 2) { // only add first 2 cards
                SwingUtilities.invokeLater(() -> {
                    add(animatedCard);
                    setComponentZOrder(animatedCard, 0); 
                    revalidate(); 
                    repaint();
                });
            }
            x++;
            y[0] -= 40; 
        }
    }

    /* 
    public boolean isPreJudgement() {
        return preJudgement;
    }
    public void setPreJudgement(boolean preJudgement) {
        this.preJudgement = preJudgement;
    }
    public boolean isPostJudgement() {
        return postJudgement;
    }
    public void setPostJudgement(boolean postJudgement) {
        this.postJudgement = postJudgement;
    }
    */


    public boolean isPostJudgement() {
        return postJudgement;
    }

    public void setPostJudgement(boolean postJudgement) {
        this.postJudgement = postJudgement;
    }

    public void setJudgementState(boolean state) {
        judgementState = state;
    }
    public boolean getJudgementState() {
        return judgementState;
    }

    public void showSelectedCounts(boolean state) {
        for (String i: temp) {
            selectedCounts.get(i).setVisible(state);
        }
    }   

    public String getColor(int i) {
        return temp[i];
    }

    public void updateTrainCardCounts() {
        for (String i: temp) {
            trainCardCounts.get(i).setText(String.valueOf(p.getTrainCards().get(i)));
        }
    }

    public void updateSelectedCounts(String i) {
        selectedCounts.get(i).setText(String.valueOf(p.getTrainCardsSelected().get(i)));
    }

    public void resetSelectedCounts() {
        for (String i: temp) {
            selectedCounts.get(i).setText("0");
        }
    }

    public ArrayList<AnimatedCard> getAnimatedPathCards() {
        return animatedPathCards;
    }

    public void setHandText(String txt) {
        text.setText(txt);
    }

    public String getHandText() {
        return text.getText();
    }

    public JButton getOkButton() {
        return okButton;
    }
    public JButton getCancelButton() {
        return cancelButton;
    }
    public void setEnabled(boolean state) {
        okButton.setEnabled(state);
        cancelButton.setEnabled(state);
        showButtons(state);
    }
    public JButton[] getPlayerTrainButtons() {
        return playerTrainButtons;
    }
    public Player getPlayer() {
        return p;
    }
    public String[] getTemp(){
        return temp;
    }

    public void addTrainCard(TrainCard drawnCard) {
        String color = drawnCard.getType();
        TreeMap<String, Integer> mp = p.getTrainCards();
        mp.put(color, mp.get(color) + 1);
    }

    public void showButtons(boolean state) {
        okButton.setVisible(state);
        cancelButton.setVisible(state);
    }
}
