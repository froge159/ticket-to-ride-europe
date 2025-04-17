package panels;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.TreeMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.Player;
import utils.Rel;

public class HandPanel extends JPanel {
    // panel for whoever is playing rn

    private Player p;

    public HandPanel(Player p) {
        this.p = p;

        setSize(Rel.W(1350), Rel.H(200));

        SwingUtilities.invokeLater(() -> {
            setLayout(new FlowLayout(FlowLayout.LEFT, Rel.W(20), 0));
            updatePanel();
        });
    }

    public void updatePanel() {
        removeAll();
        TreeMap<String, Integer> mp = p.getTrainCards();
        for (java.util.Map.Entry<String, Integer> entry: mp.entrySet()) {
            JLabel count = new JLabel(String.valueOf(entry.getValue()));
            count.setFont(new Font("Arial", Font.PLAIN, Rel.W(20)));
            add(count);
        }
        
        revalidate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        
    }
}
