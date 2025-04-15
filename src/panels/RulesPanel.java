package panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JButton;
import utils.ImageEnum;


public class RulesPanel extends JPanel {

    private JButton returnButton;
    private BufferedImage rulesBG;

    public RulesPanel() {
        returnButton = new JButton();
        rulesBG = ImageEnum.RULESBG.getImage();
        add(returnButton);
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rulesBG != null) {
            g.drawImage(rulesBG, 0, 0, this);
        }
    }
}
