package models;


import javax.swing.ImageIcon;


abstract class Card {
    private boolean isFaceUp = false;
    private ImageIcon front;

    public Card(boolean isFaceUp, ImageIcon front) {
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
