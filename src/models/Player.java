package models;
import java.util.*;

public class Player {
    private String color;
    private int trains = 45, stations = 4, points = 0;
    private TreeMap<String, Integer> trainCards;
    private ArrayList<PathCard> pathCards;
    private LinkedList<City> cities;
    private LinkedList<Path> paths;

    public Player(String c){
        trainCards = new TreeMap<>();
        pathCards = new ArrayList<>();
        cities = new LinkedList<>();
        paths = new LinkedList<>();
        color = c;

        trainCards.put("pink", 0);
        trainCards.put("white", 0);
        trainCards.put("blue", 0);
        trainCards.put("yellow", 0);
        trainCards.put("orange", 0);
        trainCards.put("black", 0);
        trainCards.put("red", 0);
        trainCards.put("green", 0);
        trainCards.put("wild", 0);
    }

    public void addTrainCard(TrainCard card){
        trainCards.put(card.getColor(), trainCards.get(card.getColor()) + 1);
    }

    public void claimRoute(/*GamePanel?*/Path route){
        if(trains > route.getLength()){
            City[] temp = route.getCities();
            for(City i : temp){
                if(!cities.contains(i)) cities.add(i);
            }
            paths.add(route);
            trains -= route.getLength();
        }
    }

    public void addPathCard(NormalPathCard card){
        pathCards.add(card);
    }

    public void buildStation(City city){
        if(stations > 0){
            city.buildStation(this);
            stations -= 1;
        }
    }

    public boolean connected(PathCard card){
        //if two cities are connected by the player's owned routes
        return false;
    }

    public int longestPath(){
        return 0;
    }

    public String getColor(){return color;}
    public int getTrains(){return trains;}
    public int getStations(){return stations;}
    public TreeMap<String, Integer> getTrainCards(){return trainCards;}
    public ArrayList<PathCard> getPathCards(){return pathCards;}
}
