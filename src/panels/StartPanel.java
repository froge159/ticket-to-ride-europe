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

    private JButton startButton;
    private BufferedImage startBG;
    private JLabel startText, infoIcon;

    public StartPanel() {
        setLayout(null);
        initComponents();

        

        add(startButton);
        add(startText);
        //add(infoIcon);
    }


    public JButton getStartButton() {
        return startButton;
    }

    public void initComponents() {
        startButton = new JButton("Start Game"); 
        startButton.setBounds(PositionEnum.STARTBUTTON.getX(), PositionEnum.STARTBUTTON.getY(), 200, 40);
        
        startBG = ImageEnum.TITLEBG.getImage();

        startText = PNGEnum.TITLETEXT.getImage();
        startText.setBounds(PositionEnum.STARTTEXT.getX(), PositionEnum.STARTTEXT.getY(), PNGEnum.TITLETEXT.getWidth(), PNGEnum.TITLETEXT.getHeight());

        infoIcon = PNGEnum.INFOICON.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startBG != null) {
            g.drawImage(startBG, 0, 0, this);
        }
    }
}
