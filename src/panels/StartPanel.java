package panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.Rel;
import utils.ImageEnum;
import utils.PNGEnum;
import utils.DimensionEnum;

public class StartPanel extends JPanel {

    private JButton startButton, infoButton;
    private BufferedImage startBG;
    private JLabel titleText;

    public StartPanel() {
        
        initComponents();

        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            add(startButton);
            add(titleText);
            add(infoButton);
        });
    }


    public JButton getStartButton() {
        return startButton;
    }

    public JButton getInfoButton() {
        return infoButton;
    }

    public void initComponents() {
        startButton = new JButton("Start Game"); 
        startButton.setBounds(Rel.X(840), Rel.Y(860), DimensionEnum.STARTBUTTON.getWidth(), DimensionEnum.STARTBUTTON.getHeight());
        
        startBG = ImageEnum.TITLEBG.getImage();

        titleText = new JLabel(PNGEnum.TITLETEXT.getImage());
        titleText.setBounds(Rel.X(450), Rel.Y(0), PNGEnum.TITLETEXT.getWidth(), PNGEnum.TITLETEXT.getHeight());
        
        infoButton = new JButton();
        infoButton.setIcon(PNGEnum.INFOICON.getImage());
        infoButton.setBounds(Rel.X(1750), Rel.Y(900), PNGEnum.INFOICON.getWidth(), PNGEnum.INFOICON.getHeight());
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
