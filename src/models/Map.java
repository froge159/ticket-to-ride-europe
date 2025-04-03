package models;
import java.util.*;

public class Map {
    //graph class implementation
    private LinkedList<City> cities;
    private LinkedList<Path> paths;

    public Map(){
        cities = new LinkedList<>();
        paths = new LinkedList<>();
    }

    public void addPath(Path path){
        paths.add(path);
    }

    public void addCity(City city){
        cities.add(city);
    }
    
    public boolean adjacent(City c1, City c2){
        for(City i : c1.getNeighbors()){
            if(i.equals(c2)){
                return true;
            }
        }
        return false;
    }
}
