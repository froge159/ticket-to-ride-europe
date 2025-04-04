package utils;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public enum PNGEnum {

    INFOICON("assets/infoicon.png", 50, 50),
    TITLETEXT("assets/titletext.png", 1000, 550);

    private final JLabel b;

    private PNGEnum(String path, int w, int h) {
        ImageIcon temp = null;
        try {
            Image img = new ImageIcon(path).getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
            temp = new ImageIcon(img);
        }
        catch(Exception e) {
            System.out.println("Failed to find path " + path);
        }
        b = new JLabel(temp);
    }


    public JLabel getImage(){
        return b;
    }

    public int getWidth() {
        return b.getWidth();
    }

    public int getHeight() {
        return b.getHeight();
    }

}