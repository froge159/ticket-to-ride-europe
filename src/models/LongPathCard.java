package models;

import java.awt.image.BufferedImage;


public class LongPathCard extends PathCard {

    public LongPathCard(String c1, String c2, int points, BufferedImage front){
        super(points, c1, c2, false,  front);
    }
}
