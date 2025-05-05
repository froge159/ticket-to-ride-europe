package models;
import java.util.*;

public class Player {
    private int number;
    private String color;
    private int trains = 4, stations = 3, points = 0;
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
    private City pendingCity = null;

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

    public void claimRoute(/*GamePanel?*/Path route, boolean station){
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

        if (!station) {
            for (PathBlock pb: route.getPath()) {
                pb.setOwnerColor(color);
            }
            route.buy(this);
        }
    }

    public void calculateScore(ArrayList<City> allCities) {
        int finalPoints = points;
        for (PathCard card: pathCards) {
            String c1 = card.getCities()[0];
            String c2 = card.getCities()[1];
            City city1 = null;
            City city2 = null;

            for (City city: allCities) {
                if (city.getName().toLowerCase().equals(c1.toLowerCase())) {
                    city1 = city;
                } else if (city.getName().toLowerCase().equals(c2.toLowerCase())) {
                    city2 = city;
                }
            }

            try {
                if (hasConnection(city1, city2)) {
                    finalPoints += card.getPoints();
                }
            }
            catch (Exception e) {
                System.out.println(city1 + " " + city2);
            }
        }

        points = finalPoints + 4 * stations;
    }

    public int getLongestPathLength() {
        Set<City> visited = new HashSet<>();
        int longestPath = 0;
    
        // Start DFS from each city in the player's graph
        for (City city : cities) {
            longestPath = Math.max(longestPath, dfs(city, visited, 0));
        }
    
        return longestPath;
    }
    
    private int dfs(City current, Set<City> visited, int currentLength) {
        visited.add(current);
        int maxLength = currentLength;
    
        for (Path path : paths) {
            // Check if the path is owned by the player and connects to the current city
            if (path.getBuyer() == this && (path.getCity1().equals(current) || path.getCity2().equals(current))) {
                City neighbor = path.getCity1().equals(current) ? path.getCity2() : path.getCity1();
    
                if (!visited.contains(neighbor)) {
                    // Recurse to the neighbor and update the maximum length
                    maxLength = Math.max(maxLength, dfs(neighbor, visited, currentLength + path.getLength()));
                }
            }
        }
    
        visited.remove(current); // Backtrack
        return maxLength;
    }

    public boolean hasConnection(City city1, City city2) {
        Set<City> visited = new HashSet<>();
        Queue<City> queue = new LinkedList<>();
    
        queue.add(city1);
        visited.add(city1);
    
        while (!queue.isEmpty()) {
            City current = queue.poll();
    
            if (current.equals(city2)) {
                return true; // Found the target city
            }
    
            for (City neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    
        return false; // No path found
    }

    public void setPendingCity(City city){
        pendingCity = city;
    }

    public City getPendingCity(){
        return pendingCity;
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

    public LinkedList<City> getCities() { return cities; }


    public void setPoints(int points) {
        this.points = points;
    }   
}
