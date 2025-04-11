package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
        replay.setFont(new Font("Arial", Font.PLAIN, 20));
        replay.setForeground(Color.WHITE);
        replay.setBounds(PositionEnum.STARTBUTTON.getX(), PositionEnum.STARTBUTTON.getY(), 200, 40);
        
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
    }
}
