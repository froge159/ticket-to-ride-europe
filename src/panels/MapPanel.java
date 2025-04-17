package panels;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.PathBlock;
import models.TTRMap;
import utils.ImageEnum;



public class MapPanel extends JPanel{

    private BufferedImage mapBG;
    private TTRMap map;

    public MapPanel() throws IOException {
        map = new TTRMap();
        
        mapBG = ImageEnum.MAPBG.getImage();

        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            setSize(mapBG.getWidth(), mapBG.getHeight());
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(mapBG, 0, 0, null);
        map.getPaths().forEach(path -> {
            for (PathBlock block : path.getPath()) {
                block.draw(g2d);
            }
        });
    }
}
