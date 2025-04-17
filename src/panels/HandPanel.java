package panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.Player;
import utils.Rel;
import utils.CardImages;

public class HandPanel extends JPanel {
    // panel for whoever is playing rn

    private Player p;
    private String[] temp = { "black", "blue", "brown", "green", "purple", "red", "white", "yellow", "wild" };

    public HandPanel(Player p) {
        this.p = p;

        setSize(Rel.W(1530), Rel.H(200));
        setOpaque(false);
        // setBorder(new javax.swing.border.LineBorder(Color.YELLOW, Rel.W(3), true));

        SwingUtilities.invokeLater(() -> {
            setLayout(new FlowLayout(FlowLayout.LEFT, Rel.W(140), 65));
            updatePanel();
        });
    }

    public void updatePanel() {
        removeAll();

        TreeMap<String, Integer> mp = p.getTrainCards();
        int x = 80;
        for (String color : temp) {
            JLabel count = new JLabel(String.valueOf(mp.get(color)));
            count.setFont(new Font("Arial", Font.PLAIN, Rel.W(30)));
            count.setForeground(Color.YELLOW);

            JButton card = new JButton(CardImages.getImg(color + "-train")); // 125/ 200
            card.setBounds(Rel.X(x), 0, Rel.W(125), Rel.H(200));
            
            x += 160;
            add(count);
            add(card);
        }
        
        revalidate();
        repaint();
    }
}
