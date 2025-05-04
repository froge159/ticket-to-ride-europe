package models;
import java.util.*;

public class Player {
    private int number;
    private String color;
    private int trains = 45, stations = 3, points = 0;
    private LongPathCard longPath = null;
    private TreeMap<String, Integer> trainCards;
    private TreeMap<String, Integer> trainCardsSelected;
    private Stack<PathCard> pathCards;
    private LinkedList<City> cities;
    private LinkedList<Path> paths;
    private boolean drawn = false;
    private Path selectedPath = null;
    private int selected = 0;
    private int extraCardsNeeded = 0;
    private ArrayList<TrainCard> lastThreeCards;

    public Player(int c, String co){
        String[] temp = { "black", "blue", "orange", "green", "purple", "red", "white", "yellow", "wild" };

        trainCards = new TreeMap<>();
        trainCardsSelected = new TreeMap<>();
        pathCards = new Stack<>();
        cities = new LinkedList<>();
        paths = new LinkedList<>();
        number = c;
        color = co;

        for (String skibidi: temp) {
            trainCards.put(skibidi, 0);
            trainCardsSelected.put(skibidi, 0);
        }
    }

    

    public String getColor(){
        return color;
    }
    public void addTrainCard(TrainCard card){
        trainCards.put(card.getType(), trainCards.get(card.getType()) + 1);
    }

    public void claimRoute(/*GamePanel?*/Path route){
        City c1 = route.getCity1();
        City c2 = route.getCity2();
        if(!cities.contains(c1)) cities.add(c1);
        if(!cities.contains(c2)) cities.add(c2);
        try {
            c1.getNeighbors().add(c2);
            c1.getNeighbors().add(c2);
        }
        catch (Exception e){
            System.out.println("Error adding edge: " + c1 + " " + c2);
        }
        paths.add(route);
        trains -= route.getLength();
        points += route.getPoints();

        for (PathBlock pb: route.getPath()) {
            pb.setOwnerColor(color);
        }
    }

    public void addPathCard(NormalPathCard card){
        pathCards.add(card);
    }

    public void setLongPathCard(LongPathCard card){
        longPath = card;
        pathCards.add(card);
    }

    public void buildStation(City city){
        if(stations > 0){
            city.buildStation(this);
            stations -= 1;
        }
    }

    public void resetTrainCardsSelected(){
        for (String color: trainCardsSelected.keySet()) {
            trainCardsSelected.put(color, 0);
        }
    }

    public void setExtraCardsNeeded(int num){
        extraCardsNeeded = num;
    }

    public int getExtraCardsNeeded(){
        return extraCardsNeeded;
    }

    public void setLastThreeCards(ArrayList<TrainCard> cards){
        lastThreeCards = cards;
    }

    public ArrayList<TrainCard> getLastThreeCards(){
        return lastThreeCards;
    }

    public void setSelected(int s) {
        selected = s;
    }
    public int getSelected() {
        return selected;
    }

    public void setStationCount(int c) {
        stations = c;
    }

    public boolean connected(PathCard card){
        //if two cities are connected by the player's owned routes
        return false;
    }

    public int longestPath(){
        return 0;
    }

    public int getNumber(){return number;}
    public int getTrains(){return trains;}
    public int getStations(){return stations;}
    public TreeMap<String, Integer> getTrainCards(){return trainCards;}
    public Stack<PathCard> getPathCards(){return pathCards;}

    public int getPoints(){return points;}

    public LongPathCard getLongPath() { return longPath; }

    public TreeMap<String, Integer> getTrainCardsSelected() {
        return trainCardsSelected;
    }

    public boolean getDrawn() { return drawn; }
    public void setDrawn(boolean d) { drawn = d; }

    public void setSelectedPath(Path p) { System.out.println("called selected path with " + p);  selectedPath = p; }
    public Path getSelectedPath() { return selectedPath; }
}
