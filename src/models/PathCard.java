package models;

abstract class PathCard extends Card{
    private int points;
    private String city1, city2;
    private boolean completed = false;

    public void setPoints(int p){points = p;}
    public void complete(){completed = true;}
    public void setCities(String one, String two){
        city1 = one;
        city2 = two;
    }

    public int getPoints(){return points;}
    public String[] getCities(){return new String[]{city1, city2};}
    public boolean isComplete(){return completed;}
}
