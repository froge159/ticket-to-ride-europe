package panels;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.ImageEnum;



public class MapPanel extends JPanel {

    private BufferedImage mapBG;

    public MapPanel() {
        
        mapBG = ImageEnum.MAPBG.getImage();

        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            setSize(mapBG.getWidth(), mapBG.getHeight());
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapBG, 0, 0, null);
    }
    
}
