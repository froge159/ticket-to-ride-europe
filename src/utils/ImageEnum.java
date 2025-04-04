package utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum ImageEnum {

    TITLEBG("assets/titlebg.png", 1950, 1200),
    INFOICON("assets/infoicon.png", 50, 50),
    TITLETEXT("assets/titletext.png", 500, 200);

    private final BufferedImage b;

    private ImageEnum(String path, int w, int h) {
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(new File(path));
            temp = scale(temp, w, h);
        }
        catch(Exception e) {
            System.out.println("Failed to find path " + path);
        }
        b = temp;
    }

    public static BufferedImage scale(BufferedImage src, int w, int h) {
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

    public BufferedImage getImage(){
        return b;
    }
}