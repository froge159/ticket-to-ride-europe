package models;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class NormalPathCard extends PathCard {

    public NormalPathCard(String c1, String c2, int points, BufferedImage front){
        super(points, c1, c2, false, front);
    }
}
