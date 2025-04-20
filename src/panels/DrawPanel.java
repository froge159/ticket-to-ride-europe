package panels;

import java.awt.Color;
import java.awt.Font;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.Player;
import utils.Rel;
import utils.CardImages;

public class DrawPanel extends JPanel {

    public DrawPanel() {
        setSize(Rel.W(1600), Rel.H(200));
        setOpaque(false);
        // setBorder(new javax.swing.border.LineBorder(Color.YELLOW, Rel.W(3), true));

        

        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            updatePanel();
        });
    }

    public void updatePanel() {
        removeAll();

        
        
        revalidate();
        repaint();
    }
}
