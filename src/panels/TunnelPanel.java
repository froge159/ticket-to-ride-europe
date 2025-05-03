package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.TrainCard;
import utils.Rel;

public class TunnelPanel extends JPanel {

    private JButton returnButton;
    private JLabel text;
    private ImageIcon[] images;
    
    public TunnelPanel() {
        returnButton = new JButton("Return to Game");
        text = new JLabel();
        images = new ImageIcon[3];

        setLayout(null);
        setSize(500, 1080);
        setOpaque(true);

        SwingUtilities.invokeLater(() -> {
            add(returnButton);
            add(text);
        });
    }

    public void updatePanel(ArrayList<TrainCard> lastThreeCards) {
        for (int i = 0; i < 3; i++) {
            images[i] = lastThreeCards.get(i).getScaledFront(200, 150);
        }
        returnButton.setBounds(Rel.X(190), Rel.Y(750), Rel.W(120), Rel.H(50));
        text.setBounds(Rel.X(100), Rel.Y(100), Rel.W(400), Rel.H(50));
        text.setForeground(Color.WHITE);
        text.setFont(new Font(null, Font.PLAIN, Rel.W(30)));
    }

    public void setText(String s) {
        text.setText(s);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(44, 25, 17));

        int y = 200;
        for (int i = 0; i < 3; i++) {
            if (images[i] != null) {
                images[i].paintIcon(this, g, Rel.X(150), Rel.Y(y));
            }
            y += 150;
        }
    }
}
