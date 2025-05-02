package utils;
import java.awt.Color;


public enum ColorEnum {

    BLACK(0, 0, 0, "black"),
    BLUE(0, 0, 255, "blue"),
    BROWN(139, 69, 19, "brown"),
    GREEN(0, 255, 0, "green"),
    PURPLE(128, 0, 128, "purple"),
    RED(255, 0, 0, "red"),
    WHITE(255, 255, 255, "white"),
    YELLOW(255, 255, 0, "yellow"),
    GRAY(128, 128, 128, "gray"),
    ORANGE(255, 165, 0, "orange"),
    PINK(255, 192, 203, "pink"),;

    private final Color c;
    private final String name;

    private ColorEnum(int v1, int v2, int v3, String n) {
        c = new Color(v1, v2, v3);
        name = n;
    }

    public Color getColor(){
        return c;
    }

    public String getName(){
        return name;
    }   

    public static Color getColor(String name){
        for(ColorEnum color: ColorEnum.values()){
            if(color.getName().equals(name)){
                return color.getColor();
            }
        }
        return null;
    }
}