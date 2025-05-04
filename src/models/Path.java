package models;

public class Path {
    //Graph edge class implementation
    private PathBlock[] path;
    private int length, points;
    private City city1, city2;
    private boolean bought = false;
    private Player buyer;
    private String type;

    public Path(PathBlock[] path, City c1, City c2){
        this.path = path;
        length = path.length;
        city1 = c1;
        city2 = c2;
        type = path[0].getType();
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
    public City getCity1(){return city1;}   
    public City getCity2(){return city2;}
    public boolean isBought(){return bought;}
    public Player getBuyer(){return buyer;}
    public int getLength(){return length;}

    public String getType(){return type;}


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Path: ");
        for (PathBlock block : path) {
            sb.append(block.getType()).append(" ");
        }
        sb.append("Length: ").append(length).append(", Points: ").append(points);
        return sb.toString();
    }
}
