package models;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import utils.ImageEnum;

public class TrainCard extends Card {
    private String type;
    private BufferedImage back;

    public TrainCard(String t, boolean isFaceUp, BufferedImage front){
        super(isFaceUp, front);
        back = ImageEnum.TRAINBACK.getImage();
        type = t;
    }

    public String getTrainCardType() {
        return type;
    }
}
