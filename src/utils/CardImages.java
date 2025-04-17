package utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CardImages {
    
    public static HashMap<String, BufferedImage> cardImages = new HashMap<>();

    public static BufferedImage addImage(String name, BufferedImage image) {
        image = scale(image, 100, 150); // assume all of these are cards
        return cardImages.put(name, image);
    }

    private static BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img = 
        new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }
}
