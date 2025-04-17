package models;

import java.awt.image.BufferedImage;


abstract class Card {
    private boolean isFaceUp = false;
    private BufferedImage front;

    public Card(boolean isFaceUp, BufferedImage front) {
        this.isFaceUp = isFaceUp;
        this.front = front;
    }

    public void flip(){
        if(isFaceUp) isFaceUp = false;
        else isFaceUp = true;
    }

    public boolean isFaceUp(){
        return isFaceUp;
    }
}
