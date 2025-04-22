package models;


import javax.swing.ImageIcon;

public class NormalPathCard extends PathCard {

    public NormalPathCard(String c1, String c2, int points, ImageIcon front){
        super(points, c1, c2, false, front);
    }

    @Override
    public String toString() {
        return "NormalPathCard{" +
                "city1='" + getCities()[0] + '\n' +
                ", city2='" + getCities()[1] + '\n' +
                ", points=" + getPoints() +
                '}';
    }
}
