package panels;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.DimensionEnum;
import utils.Dimensions;
import utils.ImageEnum;

import models.Player;
import utils.ImageEnum;
import utils.PNGEnum;
import utils.PositionEnum;

public class EndPanel extends JPanel {
    // panel for end screen
    private JButton replay;
    private BufferedImage endBG;
    private JLabel titleText;
    private JLabel winnerText;
    private JLabel[] scoreText;
   // private Player[] players;

    public EndPanel() {
        setLayout(null);
        initComponents();
        add(replay);
        add(titleText);
        add(winnerText);
        /*for (JLabel label : scoreText) {
            add(label);
        }*/
    }

    public JButton getButton() {
        return replay;
    }

    public void initComponents(){
        

        replay = new JButton("Press to return"); // Placeholder for replay button
        replay.setFont(new Font("Arial", Font.PLAIN, (int)(20*Dimensions.HEIGHT/(double)1080)));
        replay.setForeground(Color.WHITE);
        replay.setBounds(PositionEnum.STARTBUTTON.getX(), PositionEnum.STARTBUTTON.getY(), DimensionEnum.STARTBUTTON.getWidth(), DimensionEnum.STARTBUTTON.getHeight());
        
        endBG = ImageEnum.TITLEBG.getImage();

        titleText = new JLabel("Game Over"); // Placeholder for title text
        titleText.setBounds(PositionEnum.TITLETEXT.getX(), PositionEnum.TITLETEXT.getY(), PNGEnum.TITLETEXT.getWidth(), PNGEnum.TITLETEXT.getHeight());
        
        winnerText = new JLabel("Winner: Player 1"); // Placeholder for winner text
        winnerText.setBounds(PositionEnum.STARTBUTTON.getX(), PositionEnum.STARTBUTTON.getY() - 20, 200, 40);
/* 
        scoreText = new JLabel[4];
        for (int i = 0; i < players.length; i++) {
            scoreText[i] = new JLabel("Player " + (i + 1) + ": " + (i * 10)); // Placeholder for scores
            scoreText[i].setBounds(200 + (i*200), 600, 200, 40); // Placeholder for score text
        }*/

        /*for (JLabel label : scoreText) {
            add(label);
        }*/
        replay.setOpaque(false);
        replay.setContentAreaFilled(false);
        replay.setBorderPainted(false);
        replay.setFocusPainted(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (endBG != null) {
            g.drawImage(endBG, 0, 0, this);
        }
        // Cast Graphics to Graphics2D to enable advanced features
    Graphics2D g2d = (Graphics2D) g;

    // Set transparency using AlphaComposite
    float transparency = 0.5f; // 0.0f is fully transparent, 1.0f is fully opaque
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));

    // Set the color for the rectangle
    g2d.setColor(Color.BLACK);

    // Draw the semi-transparent rectangle
    g2d.fillRect(PositionEnum.BLACKRECT.getX(), PositionEnum.BLACKRECT.getY(), DimensionEnum.BLACKRECT.getWidth(), DimensionEnum.BLACKRECT.getHeight());

    // Reset the composite to default (fully opaque) for subsequent drawings
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

    g.setFont(new Font("Arial", Font.PLAIN, (int)(150*Dimensions.HEIGHT/(double)1080)));
    g.setColor(Color.WHITE);
    g.drawString("GAME OVER", PositionEnum.GAMETEXT.getX(), PositionEnum.GAMETEXT.getY() + 50);
    g.drawString("Winner: Player 1", PositionEnum.STARTBUTTON.getX(), PositionEnum.STARTBUTTON.getY() - 20);
    }
}
