package panels;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.ImageEnum;
import utils.PNGEnum;
import utils.PositionEnum;

public class StartPanel extends JPanel {

    private JButton startButton, infoButton;
    private BufferedImage startBG;
    private JLabel titleText;

    public StartPanel() {
        setLayout(null);
        initComponents();

        

        add(startButton);
        add(titleText);
        add(infoButton);
    }


    public JButton getStartButton() {
        return startButton;
    }

    public JButton getInfoButton() {
        return infoButton;
    }

    public void initComponents() {
        startButton = new JButton("Start Game"); 
        startButton.setBounds(PositionEnum.STARTBUTTON.getX(), PositionEnum.STARTBUTTON.getY(), 200, 40);
        
        startBG = ImageEnum.TITLEBG.getImage();

        titleText = PNGEnum.TITLETEXT.getImage();
        titleText.setBounds(PositionEnum.TITLETEXT.getX(), PositionEnum.TITLETEXT.getY(), PNGEnum.TITLETEXT.getWidth(), PNGEnum.TITLETEXT.getHeight());
        
        infoButton = new JButton();
        infoButton.add(PNGEnum.INFOICON.getImage());
        

        infoButton.setBounds(PositionEnum.INFOICON.getX(), PositionEnum.INFOICON.getY(), PNGEnum.INFOICON.getWidth(), PNGEnum.INFOICON.getHeight() + 10);
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
