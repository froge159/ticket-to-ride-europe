package models;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import utils.PNGEnum;

public class TrainCard extends Card {
    private String type;
    private ImageIcon back;

    public TrainCard(String t, boolean isFaceUp, ImageIcon front){
        super(isFaceUp, front);
        back = PNGEnum.TRAINBACK.getImage();
        type = t;
    }

    public String getTrainCardType() {
        return type;
    }
}
