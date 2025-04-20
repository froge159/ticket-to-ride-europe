package utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum ImageEnum {

    // enum for non-png images

    TITLEBG("assets/titlebg.png"),
    ENDBG("assets/endBG.png"),
    GAMEBG("assets/gamebg.png"),
    RULESBG("assets/rulesbg.png"),
    MAPBG("assets/mapBG.png");
    

    private final BufferedImage b;
    private int w;
    private int h;

    private ImageEnum(String path) {
        BufferedImage temp = null;
            this.w = DimensionEnum.valueOf(name()).getWidth();
            this.h = DimensionEnum.valueOf(name()).getHeight();
        
        
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