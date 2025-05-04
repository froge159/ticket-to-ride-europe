package models;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import utils.ColorEnum;

public class TTRMap {
    //graph class implementation
    private LinkedList<City> adjList;
    private LinkedList<Path> paths;
    private ArrayList<City> cities;

    public TTRMap() throws IOException{
        adjList = new LinkedList<>();
        paths = new LinkedList<>();
        cities = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("assets/data/cities.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            String cityName = st.nextToken();
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            City city = new City(cityName, x, y);
            adjList.add(city);
            cities.add(city);
        }


        br = new BufferedReader(new FileReader("assets/data/pathBlocks.txt"));
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            String city1 = st.nextToken();
            String city2 = st.nextToken();
            int blockCount = Integer.parseInt(st.nextToken());

            PathBlock[] pathBlocks = new PathBlock[blockCount];
            for (int i = 0; i < blockCount; i++) {
                line = br.readLine();
                st = new StringTokenizer(line);
                pathBlocks[i] = new PathBlock(
                    ColorEnum.getColor(st.nextToken()),
                    st.nextToken(),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Double.parseDouble(st.nextToken())
                );
            }
            paths.add(new Path(pathBlocks, getCity(city1), getCity(city2)));
            br.readLine();
        }
    }

    public Path getParallelPath(Path p) {
        for (Path path : paths) {
            if (path.getCity1().equals(p.getCity1()) && path.getCity2().equals(p.getCity2()) && path != p) {
                return path;
            }
        }
        return null;
    }

    public void addPath(Path path){
        paths.add(path);
    }

    

    public City getCity(String name){
        for(City i : cities){
            if(i.getName().equals(name)){
                return i;
            }
        }
        return null;
    }   
    
    public boolean adjacent(City c1, City c2){
        for(City i : c1.getNeighbors()){
            if(i.equals(c2)){
                return true;
            }
        }
        return false;
    }

    public LinkedList<City> getAdjList(){
        return adjList;
    }
    public LinkedList<Path> getPaths(){
        return paths;
    }

    public ArrayList<City> getCities(){
        return cities;
    }
}
