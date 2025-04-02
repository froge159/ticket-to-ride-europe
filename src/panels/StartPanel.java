package panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.ImageEnum;
import utils.PNGEnum;

public class StartPanel extends JPanel {

    private JButton startButton;
    private BufferedImage startBG;
    private JLabel startText, infoIcon;

    public StartPanel() {
        startButton = new JButton();
        startBG = ImageEnum.TITLEBG.getImage();
        startText = new JLabel(PNGEnum.TITLETEXT.getImage());
        infoIcon = new JLabel(PNGEnum.INFOICON.getImage());

        add(startButton);
        add(startText);
        add(infoIcon);
    }


    public JButton getStartButton() {
        return startButton;
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startBG != null) {
            g.drawImage(startBG, 0, 0, this);
        }
    }
}
