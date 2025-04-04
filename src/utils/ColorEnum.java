package utils;
import java.awt.Color;

public enum ColorEnum {

    YELLOW(255, 255, 0),
    BLUE(65, 105, 225),
    GREEN(0, 128, 0),
    RED(178, 34, 34);

    private final Color c;

    private ColorEnum(int v1, int v2, int v3) {
        c = new Color(v1, v2, v3);
    }

    public Color getColor(){
        return c;
    }
}