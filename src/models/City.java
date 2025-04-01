package models;
import java.util.*;

public class City {
    //graph node class implementation
    private String name;
    private int x, y;
    private boolean hasStation = false;
    private Player stationOwner;
    private ArrayList<City> neighbors;

    public City(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public void buildStation(Player player){
        hasStation = true;
        stationOwner = player;
    }
}
