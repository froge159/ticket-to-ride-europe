package panels;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.PathCard;
import models.Player;
import utils.Rel;
import utils.CardImages;

public class HandPanel extends JPanel {
    // panel for whoever is playing rn

    private Player p;
    private String[] temp = { "black", "blue", "brown", "green", "purple", "red", "white", "yellow", "wild" };
    private ArrayList<AnimatedCard> animatedPathCards;

    public HandPanel(Player p) {
        this.p = p;

        setSize(Rel.W(1700), Rel.H(1080));
        setOpaque(false);
        setBorder(new javax.swing.border.LineBorder(Color.YELLOW, Rel.W(3), true));

        setLayout(null);
        updatePanel();
    }

    public void updatePanel() {
        removeAll();

        TreeMap<String, Integer> mp = p.getTrainCards();
        TreeMap<String, Integer> mpSelected = p.getTrainCardsSelected();
        Stack<PathCard> pc = p.getPathCards();
        animatedPathCards = new ArrayList<>();

        int jlabelX = 70; 
        int cardX = 17;
        for (String color : temp) {
            JLabel count = new JLabel(String.valueOf(mp.get(color)));
            count.setFont(new Font("Arial", Font.PLAIN, Rel.W(30)));
            count.setForeground(Color.YELLOW);
            count.setBounds(Rel.X(jlabelX), Rel.Y(945), Rel.W(30), Rel.H(30));

            JLabel clickCount = new JLabel (String.valueOf(mpSelected.get(color)));
            clickCount.setFont(new Font("Arial", Font.BOLD, Rel.W(15)));
            clickCount.setForeground(Color.RED);
            clickCount.setBounds(Rel.X(jlabelX + 5), Rel.Y(980), Rel.W(25), Rel.H(25));

            JButton card = new JButton(CardImages.getImg(color + "-train")); // 125 /200
            card.setBounds(Rel.X(cardX), Rel.Y(880), Rel.W(125), Rel.H(200));
            card.setOpaque(false);
            card.setContentAreaFilled(false);
            card.setBorderPainted(false);
            
            jlabelX += 150;
            cardX += 150; 
            SwingUtilities.invokeLater(() -> {
                add(count);
                add(clickCount);
                add(card);
                setComponentZOrder(count, 0);
                setComponentZOrder(clickCount, 1);
                setComponentZOrder(card, 2);
            });
        }

        final int[] y = { 935 };
        int x = 0;
        Iterator<PathCard> it = pc.iterator();
        while (it.hasNext()) {
            PathCard currentCard = it.next();
            AnimatedCard animatedCard = new AnimatedCard(currentCard, Rel.W(200), Rel.H(125), Rel.X(1370), Rel.Y(y[0]));
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
        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }

    public ArrayList<AnimatedCard> getAnimatedPathCards() {
        return animatedPathCards;
    }

    public void setWarningText(String txt) {
        JLabel warning = new JLabel(txt);
        warning.setFont(new Font("Arial", Font.BOLD, Rel.W(20)));
        warning.setForeground(Color.RED);
        warning.setBounds(Rel.X(17), Rel.Y(845), Rel.W(1000), Rel.H(50));
        SwingUtilities.invokeLater(() -> {
            add(warning);
            revalidate();
            repaint();
        });
    }
}
