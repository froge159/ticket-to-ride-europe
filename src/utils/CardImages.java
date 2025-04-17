package utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class CardImages {
    
    public static HashMap<String, ImageIcon> cardImages = new HashMap<>();

    public static ImageIcon addImage(String name, BufferedImage image, int w, int h) {
        return cardImages.put(name, new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH)));
    }

    public static ImageIcon getImg(String name) {
        return cardImages.get(name);
    }


}
