package models;

public class PathBlock {
    private String color, type;
    private int deg, x, y;

    public PathBlock(String col, String ty, int d, int x, int y){
        color = col;
        type = ty;
        deg = d;
        this.x = x;
        this.y = y;
    }

    public String getColor(){return color;}
    public String getType(){return type;}
    public int[] getPos(){return new int[]{x, y, deg};}
}
