package models;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;

abstract class Card {
    private boolean isFaceUp = false;

    public void flip(){
        if(isFaceUp) isFaceUp = false;
        else isFaceUp = true;
    }

    public boolean getFaceUp(){
        return isFaceUp;
    }
}
