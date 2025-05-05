package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.Dimensions;
import utils.ImageEnum;

import utils.Rel;

public class EndPanel extends JPanel {
    // panel for end screen
    private JButton replay;
    private BufferedImage endBG;
    private JLabel[] playersText;
    private JLabel[] scoreText;
    private HandPanel[] handPanels;
    //private JLabel greenText, redText, yellowText, blueText;
   // private Player[] players;

    public EndPanel(HandPanel[] handPanels) {
        this.handPanels = handPanels;
        setSize(Rel.W(1920), Rel.H(1080));
        setLayout(null);
        initComponents();
        setVisible(true);
    }

    public void initComponents(){
        

        replay = new JButton("Return to Game"); // Placeholder for replay button
        replay.setFont(new Font("Arial", Font.PLAIN, (int)(Rel.H(20))));
        replay.setForeground(Color.WHITE);
        replay.setBounds(Rel.X(840), Rel.Y(860), 200, 40);
        
        endBG = ImageEnum.ENDBG.getImage();
        
        playersText = new JLabel[4];
        scoreText = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            playersText[i] = new JLabel("Player " + String.valueOf((i + 1)));
            playersText[i].setBounds(Rel.X(350 + i*385), Rel.Y(672), Rel.W(204), Rel.H(53));
            playersText[i].setForeground(Color.WHITE);
            //scoreText[i] = new JLabel("Player " + (i + 1) + ": " + (i * 10)); // Placeholder for scores
            scoreText[i] = new JLabel(handPanels[i].getPlayer().getPoints() + " points");
            scoreText[i].setBounds(Rel.X(350 + i*385), Rel.Y(697), Rel.W(204), Rel.H(53)); // Placeholder for score text
            scoreText[i].setForeground(Color.WHITE);
            scoreText[i].setFont(new Font("Arial", Font.PLAIN, (int)(Rel.H(20))));
            playersText[i].setFont(new Font("Arial", Font.PLAIN, (int)(Rel.H(20))));
        }

        /*for (JLabel label : scoreText) {
            add(label);
        }*/
        replay.setOpaque(false);
        replay.setContentAreaFilled(false);
        replay.setBorderPainted(false);
        replay.setFocusPainted(false);



        SwingUtilities.invokeLater(() ->{
            add(replay);
            for (JLabel label : scoreText) {
                add(label);
            }
            for (JLabel label : playersText){
                add(label);
            }
        });
    }

    public void updatePanel() {
        for (int i = 0; i < 4; i++) {
            scoreText[i].setText(handPanels[i].getPlayer().getPoints() + " points");
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (endBG != null) {
            g.drawImage(endBG, 0, 0, this);
        }
    }

    public JButton getButton() {
        return replay;
    }
    public BufferedImage getEndBG(){
        return endBG;
    }
    public JLabel[] getPlayersText() {
        return playersText;
    }
    public JLabel[] getScoreText() {
        return scoreText;
    }

}
