package panels;

import java.awt.Graphics;

import javax.swing.JPanel;
import utils.ColorEnum;

public class RulesPanel extends JPanel {

    public RulesPanel() {
        setBackground(ColorEnum.RED.getColor());
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(ColorEnum.RED.getColor()); 
    }
}
