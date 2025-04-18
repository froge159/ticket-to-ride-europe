package models;

public class Path {
    //Graph edge class implementation
    private PathBlock[] path;
    private int length, points;
    private City city1, city2;
    private boolean bought = false;
    private Player buyer;

    public Path(PathBlock[] path, City c1, City c2){
        this.path = path;
        length = path.length;
        city1 = c1;
        city2 = c2;

        switch(length){
            case 1: points = 1; break;
            case 2: points = 2; break;
            case 3: points = 4; break;
            case 4: points = 7; break;
            case 6: points = 15; break;
            case 8: points = 21; break;
        }
    }

    public void buy(Player player){
        bought = true;
        buyer = player;
    }

    public PathBlock[] getPath(){return path;}
    public int getPoints(){return points;}
    public City[] getCities(){return new City[]{city1, city2};}
    public boolean isBought(){return bought;}
    public Player getBuyer(){return buyer;}
    public int getLength(){return length;}
}
