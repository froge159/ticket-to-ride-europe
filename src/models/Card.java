package models;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;

abstract class Card {
    private BufferedImage frontImg, backImg;
    private boolean isFaceUp = false;

    public void setImg(String type, BufferedImage img){
        if(type.equals("front")) frontImg = img;
        else backImg = img;
    }

    public BufferedImage getImg(String type){
        if(type.equals("front")) return frontImg;
        else return backImg;
    }

    public void flip(){
        if(isFaceUp) isFaceUp = false;
        else isFaceUp = true;
    }

    public boolean getFaceUp(){
        return isFaceUp;
    }
}
