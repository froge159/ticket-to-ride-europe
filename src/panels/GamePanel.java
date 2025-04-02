package panels;

import java.awt.Graphics;

import javax.swing.JPanel;
import utils.ColorEnum;

public class GamePanel extends JPanel {

    
    public GamePanel() {
        setBackground(ColorEnum.YELLOW.getColor());
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(ColorEnum.YELLOW.getColor()); 
    }
}
