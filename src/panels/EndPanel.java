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
    //private JLabel greenText, redText, yellowText, blueText;
   // private Player[] players;

    public EndPanel() {
        setLayout(null);
        initComponents();
        SwingUtilities.invokeLater(() ->{
            setLayout(null);
            add(replay);
            for (JLabel label : scoreText) {
                add(label);
            }
            for (JLabel label : playersText){
                add(label);
            }
        });
    }

    public JButton getButton() {
        return replay;
    }

    public void initComponents(){
        

        replay = new JButton("Press to return"); // Placeholder for replay button
        replay.setFont(new Font("Arial", Font.PLAIN, (int)(Rel.H(20))));
        replay.setForeground(Color.WHITE);
        replay.setBounds(Rel.X(840), Rel.Y(860), 200, 40);
        
        endBG = ImageEnum.ENDBG.getImage();
        
        playersText = new JLabel[4];
        scoreText = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            playersText[i] = new JLabel("yahoo!");
            playersText[i].setBounds(Rel.X(350) + (i*385), Rel.Y(672), Rel.W(204), Rel.H(53));
            playersText[i].setForeground(Color.WHITE);
            //scoreText[i] = new JLabel("Player " + (i + 1) + ": " + (i * 10)); // Placeholder for scores
            scoreText[i] = new JLabel("" + i*10 + " points");
            scoreText[i].setBounds(Rel.X(350) + (i*385), Rel.Y(697), Rel.W(204), Rel.H(53)); // Placeholder for score text
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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (endBG != null) {
            g.drawImage(endBG, 0, 0, this);
        }
    /* 
        // Cast Graphics to Graphics2D to enable advanced features
    Graphics2D g2d = (Graphics2D) g;

    // Set transparency using AlphaComposite
    float transparency = 0.5f; // 0.0f is fully transparent, 1.0f is fully opaque
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));

    // Set the color for the rectangle
    g2d.setColor(Color.BLACK);

    // Draw the semi-transparent rectangle
    g2d.fillRect(Rel.X(170), Rel.Y(120), Rel.W(1580), Rel.H(840));

    // Reset the composite to default (fully opaque) for subsequent drawings
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

    g.setFont(new Font("Arial", Font.PLAIN, (int)(150*Dimensions.HEIGHT/(double)1080)));
    g.setColor(Color.WHITE);
    g.drawString("GAME OVER", Rel.X(500), Rel.Y(250));
    */
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.PLAIN, (int)(50*Dimensions.HEIGHT/(double)1080)));
    g.drawString("Winner: Player 1", Rel.X(770), Rel.Y(840));
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
