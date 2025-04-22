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

    public ImageIcon getScaledFront(int w, int h) {
        ImageIcon res = new ImageIcon(getFront().getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH));
        return res;
    }
}
