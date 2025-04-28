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

        BufferedReader br = new BufferedReader(new FileReader("assets/data/pathBlocks.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            String city1 = st.nextToken();
            String city2 = st.nextToken();
            int blockCount = Integer.parseInt(st.nextToken());
            
                  
            for (int i = 0; i < blockCount; i++) {
                line = br.readLine();
                st = new StringTokenizer(line);
                
            }
        }
    }



    public void addPath(Path path){
        paths.add(path);
    }

    public void addEdge(City c1, City c2){
        if (!cities.contains(c1)) {
            cities.add(c1);
        }
        if (!cities.contains(c2)) {
            cities.add(c2);
        }
        c1.getNeighbors().add(c2);
        c2.getNeighbors().add(c1);
    }
    
    public boolean adjacent(City c1, City c2){
        for(City i : c1.getNeighbors()){
            if(i.equals(c2)){
                return true;
            }
        }
        return false;
    }

    public LinkedList<City> getCities(){
        return cities;
    }
    public LinkedList<Path> getPaths(){
        return paths;
    }
}
