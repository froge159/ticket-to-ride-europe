package models;


import javax.swing.ImageIcon;

import utils.PNGEnum;


abstract class PathCard extends Card {
    private int points;
    private String city1, city2;
    private boolean completed = false;
    private ImageIcon back;

    public PathCard(int p, String c1, String c2, boolean isFaceUp, ImageIcon front) {
        super(isFaceUp, front);
        points = p;
        city1 = c1; city2 = c2;
        back = PNGEnum.PATHBACK.getImage();
    }

    public void setPoints(int p){points = p;}
    public void complete(){completed = true;}
    public void setCities(String one, String two){
        city1 = one;
        city2 = two;
    }

    public int getPoints(){return points;}
    public String[] getCities(){return new String[]{city1, city2};}
    public boolean isComplete(){return completed;}
}
