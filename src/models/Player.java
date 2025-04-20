package models;
import java.util.*;

public class Player {
    private String color;
    private int trains = 45, stations = 4, points = 0;
    private LongPathCard longPath = null;
    private TreeMap<String, Integer> trainCards;
    private TreeMap<String, Integer> trainCardsSelected;
    private Stack<PathCard> pathCards;
    private LinkedList<City> cities;
    private LinkedList<Path> paths;

    public Player(String c){
        String[] temp = { "black", "blue", "brown", "green", "purple", "red", "white", "yellow", "wild" };

        trainCards = new TreeMap<>();
        trainCardsSelected = new TreeMap<>();
        pathCards = new Stack<>();
        cities = new LinkedList<>();
        paths = new LinkedList<>();
        color = c;

        for (String color: temp) {
            trainCards.put(color, 0);
            trainCardsSelected.put(color, 0);
        }
    }

    public void addTrainCard(TrainCard card){
        trainCards.put(card.getTrainCardType(), trainCards.get(card.getTrainCardType()) + 1);
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
    public Stack<PathCard> getPathCards(){return pathCards;}

    public int getPoints(){return points;}

    public LongPathCard getLongPath() { return longPath; }

    public TreeMap<String, Integer> getTrainCardsSelected() {
        return trainCardsSelected;
    }
}
