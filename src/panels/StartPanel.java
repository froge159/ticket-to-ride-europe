package panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Rel;
import utils.ImageEnum;
import utils.PNGEnum;

public class StartPanel extends JPanel {

    private JButton startButton, infoButton;
    private BufferedImage startBG;
    private JLabel titleText;

    public StartPanel() {
        
        initComponents();

        //SwingUtilities.invokeLater(() -> {
            setLayout(null);
            add(startButton);
            add(titleText);
            add(infoButton);
        //});
    }


    public JButton getStartButton() {
        return startButton;
    }
    public JButton getInfoButton() {
        return infoButton;
    }
    public BufferedImage getStartBG() {
        return startBG;
    }
    public JLabel titleText(){
        return titleText;
    }

    public void initComponents() {
        startButton = new JButton("Start Game"); 
        startButton.setBounds(Rel.X(840), Rel.Y(860), Rel.W(200), Rel.H(40));
        
        startBG = ImageEnum.TITLEBG.getImage();

        titleText = new JLabel(PNGEnum.TITLETEXT.getImage());
        titleText.setBounds(Rel.X(450), Rel.Y(0), PNGEnum.TITLETEXT.getWidth(), PNGEnum.TITLETEXT.getHeight());
        
        infoButton = new JButton();
        infoButton.setIcon(PNGEnum.INFOICON.getImage());
        infoButton.setBounds(Rel.X(1750), Rel.Y(900), Rel.W(70), Rel.H(70));
        infoButton.setOpaque(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setBorderPainted(false);
        infoButton.setFocusPainted(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startBG != null) {
            g.drawImage(startBG, 0, 0, this);
        }
    }

}
