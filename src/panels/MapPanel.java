package panels;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import models.PathBlock;
import models.TTRMap;
import utils.ImageEnum;



public class MapPanel extends JPanel{

    private BufferedImage mapBG;
    private TTRMap map;
    private boolean pathDisabled, cityDisabled;

    public MapPanel() throws IOException {
        map = new TTRMap();
        mapBG = ImageEnum.MAPBG.getImage();

        setLayout(null);
        setSize(mapBG.getWidth(), mapBG.getHeight());
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
        map.getCities().forEach(city -> city.draw(g2d));
    }

    public BufferedImage getMapBG(){
        return mapBG;
    }
    public TTRMap getMap(){
        return map;
    }
    
    public void setPathDisabled(boolean disabled) {
        this.pathDisabled = disabled;
    }

    public boolean pathIsDisabled() {
        return pathDisabled;
    }

    public void setCityDisabled(boolean disabled) {
        this.cityDisabled = disabled;
    }

    public boolean cityIsDisabled() {
        return cityDisabled;
    }
}
