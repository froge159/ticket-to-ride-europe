package panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JButton;

import utils.PositionEnum;
import utils.DimensionEnum;
import utils.ImageEnum;


public class RulesPanel extends JPanel {

    private JButton returnButton;
    private BufferedImage rulesBG;

    public RulesPanel() {
        setLayout(null);
        returnButton = new JButton("Press to Return");
        returnButton.setBounds(PositionEnum.RETURNBUTTON.getX(), PositionEnum.RETURNBUTTON.getY(), DimensionEnum.RETURNBUTTON.getWidth(), DimensionEnum.RETURNBUTTON.getHeight());
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
