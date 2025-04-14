package utils;


public enum PositionEnum {

    // enum for the positions of the buttons and images on the screen

    STARTBUTTON(860, 860),
    TITLETEXT(450, 0),
    INFOICON(1650, 900),
    BLACKRECT(150, 100),
    GAMETEXT(540, 200);

    private final int x, y;

    private PositionEnum(int origX, int origY) {
        x = (int) (Dimensions.WIDTH * (origX / (double) 1920));
        y = (int) (Dimensions.HEIGHT * (origY / (double) 1080));
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    

}