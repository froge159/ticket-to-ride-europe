package models;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.*;

import utils.Rel;

public class City {
    //graph node class implementation
    private String name;
    private int x, y;
    private boolean hasStation = false;
    private Player stationOwner;
    private ArrayList<City> neighbors;

    public City(String name, int x, int y){
        neighbors = new ArrayList<>();
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public void buildStation(Player player){
        hasStation = true;
        stationOwner = player;
        player.setStationCount(player.getStations() - 1);
    }

    public String getName(){return name;}

    public boolean equals(City temp){
        if(name.equals(temp.getName())) return true;
        return false;
    }

    public ArrayList<City> getNeighbors(){return neighbors;}

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(Rel.X(x - 10), Rel.Y(y - 10), Rel.W(20), Rel.H(20));
    }

    public boolean contains(Point p) {
        if(p.x >= Rel.X(x - 10) && p.x <= Rel.X(x + 10) && p.y >= Rel.Y(y - 10) && p.y <= Rel.Y(y + 10)){
            return true;
        }
        return false;
    }
}
