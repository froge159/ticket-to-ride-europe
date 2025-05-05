package utils;


public enum DimensionEnum {

    STARTBUTTON(200, 40),
    TITLETEXT(1000, 550),

    REDPFP(50, 50),
    BLUEPFP(50, 50),
    YELLOWPFP(50, 50),
    GREENPFP(50, 50),

    PLAYERPANELIMG(163, 400),
    STATIONBUTTON(170, 60),
    PATHBACK(50, 70),
    TRAINBACK(200, 125),
    TRAINBACKBW(200, 125),
    TICKETBACK(200, 125),
    TICKETBACKBW(200, 125),
    REDTRAINICON(50, 50),
    BLUETRAINICON(50, 50),
    YELLOWTRAINICON(50, 50),
    GREENTRAINICON(50, 50),
    REDSTATION(50, 50),
    BLUESTATION(50, 50),
    YELLOWSTATION(50, 50),
    GREENSTATION(50, 50),
    INFOICON(70, 70);

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