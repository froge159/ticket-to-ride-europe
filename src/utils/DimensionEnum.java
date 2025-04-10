package utils;


public enum DimensionEnum {

    STARTBUTTON(200, 40),
    INFOICON(160, 100),
    TITLETEXT(1000, 550),

    TITLEBG(1950, 1200),
    REDPFP(50, 50),
    BLUEPFP(50, 50),
    YELLOWPFP(50, 50),
    GREENPFP(50, 50);

    private final int w, h;

    private DimensionEnum(int w, int h) {
        this.w = (int) (Dimensions.WIDTH * (w / (double) 1920));
        this.h = (int) (Dimensions.HEIGHT * (h / (double) 1080));
        
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

}