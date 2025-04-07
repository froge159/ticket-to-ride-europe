package utils;


public enum PositionEnum {
    // returns new position of elements depending on original proportion

    STARTBUTTON(860, 860),
    TITLETEXT(450, 0),
    INFOICON(1650, 900);

    private final int x, y;

    private PositionEnum(int origX, int origY) {
        x = (int) (Dimensions.WIDTH * (origX / (double) 1600));
        y = (int) (Dimensions.HEIGHT * (origY / (double) 1200));
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    

}