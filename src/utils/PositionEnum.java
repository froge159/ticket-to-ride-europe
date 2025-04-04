package utils;


public enum PositionEnum {
    // returns new position of elements depending on original proportion

    STARTBUTTON(860, 300),
    STARTTEXT(860, 200);

    private final int x, y;

    private PositionEnum(int origX, int origY) {
        x = (int) (Dimensions.WIDTH * (origX / (double) 1600));
        y = (int) (Dimensions.HEIGHT * (origX / (double) 1200));
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    

}