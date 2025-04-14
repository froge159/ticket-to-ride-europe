package utils;


public enum DimensionEnum {

    STARTBUTTON(200, 40),
    INFOICON(70, 70),
    TITLETEXT(1000, 550),

    TITLEBG(1920, 1080),
    REDPFP(50, 50),
    BLUEPFP(50, 50),
    YELLOWPFP(50, 50),
    GREENPFP(50, 50),

    PLAYERPANELIMG(163, 400),
    STATIONBUTTON(170, 60),
    GAMEBG(1920, 1080),
    RULESBG(1920, 1080);

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