package utils;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public enum PNGEnum {

    // enum for png images

    INFOICON("assets/infoicon.png"),
    TITLETEXT("assets/titletext.png"),
    //REDPFP("assets/avatars/redcircleavatar.png"),
    //BLUEPFP("assets/avatars/bluecircleavatar.png"),
    //YELLOWPFP("assets/avatars/yellowcircleavatar.png"),
    //GREENPFP("assets/avatars/greencircleavatar.png"),
    PLAYERPANELIMG("assets/playerPanelImage.png"),
    STATIONBUTTON("assets/stationButton.png");

    private final ImageIcon b;
    private final int w, h;

    private PNGEnum(String path) {
        this.w = DimensionEnum.valueOf(name()).getWidth();
        this.h = DimensionEnum.valueOf(name()).getHeight();

        ImageIcon temp = null;
        try {
            Image img = new ImageIcon(path).getImage().getScaledInstance(this.w, this.h, java.awt.Image.SCALE_SMOOTH);
            temp = new ImageIcon(img);
        }
        catch(Exception e) {
            System.out.println("Failed to find path " + path);
        }
        b = temp;
    }


    public ImageIcon getImage(){
        return b;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }
}