package utils;
import java.awt.Image;

import javax.swing.ImageIcon;

public enum PNGEnum {

    // enum for png images

    INFOICON("assets/infoicon.png"),
    TITLETEXT("assets/titletext.png"),
    //REDPFP("assets/avatars/redcircleavatar.png"),
    //BLUEPFP("assets/avatars/bluecircleavatar.png"),
    //YELLOWPFP("assets/avatars/yellowcircleavatar.png"),
    //GREENPFP("assets/avatars/greencircleavatar.png"),
    PLAYERPANELIMG("assets/playerPanelImage.png"),
    STATIONBUTTON("assets/stationButton.png"),
    TRAINBACK("assets/trainCardBack.png"),
    TRAINBACKBW("assets/trainCardBackBW.png"),
    PATHBACK("assets/destinationticketback.png"),
    TICKETBACK("assets/ticketBack.png"),
    TICKETBACKBW("assets/ticketBackBW.png"),
    REDTRAINICON("assets/red-train-icon.png"),
    BLUETRAINICON("assets/blue-train-icon.png"),
    YELLOWTRAINICON("assets/yellow-train-icon.png"),
    GREENTRAINICON("assets/green-train-icon.png"),
    REDSTATION("assets/red-station.png"),
    BLUESTATION("assets/blue-station.png"),
    YELLOWSTATION("assets/yellow-station.png"),
    GREENSTATION("assets/green-station.png");

    private final ImageIcon b;
    private final int w, h;

    private PNGEnum(String path) {
        this.w = DimensionEnum.valueOf(name()).getWidth();
        this.h = DimensionEnum.valueOf(name()).getHeight();

        ImageIcon temp = null;
        try {
            Image img = new ImageIcon(path).getImage().getScaledInstance(this.w, this.h, java.awt.Image.SCALE_SMOOTH);
            temp = new ImageIcon(img);
        }
        catch(Exception e) {
            System.out.println("Failed to find path " + path);
        }
        b = temp;
    }

    public static Image getColoredTrainImage(String color) {
        switch (color) {
            case "red":
                return REDTRAINICON.b.getImage();
            case "blue":
                return BLUETRAINICON.b.getImage();
            case "yellow":
                return YELLOWTRAINICON.b.getImage();
            case "green":
                return GREENTRAINICON.b.getImage();
            default:
                return null;
        }
    }

    public static Image getColoredStationImage(String color) {
        switch (color) {
            case "red":
                return REDSTATION.b.getImage();
            case "blue":
                return BLUESTATION.b.getImage();
            case "yellow":
                return YELLOWSTATION.b.getImage();
            case "green":
                return GREENSTATION.b.getImage();
            default:
                return null;
        }
    }


    public ImageIcon getImage(){
        return b;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }
}