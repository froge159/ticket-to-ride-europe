package models;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TTRMap {
    //graph class implementation
    private LinkedList<City> cities;
    private LinkedList<Path> paths;

    public TTRMap() throws IOException{
        cities = new LinkedList<>();
        paths = new LinkedList<>();

        BufferedReader br = new BufferedReader(new FileReader("assets/data/mapRoutes.txt"));
        /* 
        String line;
        while((line = br.readLine()) != null){
            StringTokenizer st2 = new StringTokenizer(line);
            String city1 = st2.nextToken();
            String city2 = st2.nextToken();
            int length = Integer.parseInt(st2.nextToken());
            //paths.add(new Path(new PathBlock[length], new City(city1), new City(city2)));
        //    st2 = new StringTokenizer(br.readLine());
        }*/
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
