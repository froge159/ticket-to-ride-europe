package models;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TTRMap {
    //graph class implementation
    private LinkedList<City> adjList;
    private LinkedList<Path> paths;
    private ArrayList<City> cities;

    public TTRMap() throws IOException{
        adjList = new LinkedList<>();
        paths = new LinkedList<>();
        cities = new ArrayList<>();
        Map<String, Integer> colorMap = new HashMap<>();
        colorMap.put("black", Color.BLACK.getRGB());
        colorMap.put("blue", Color.BLUE.getRGB());
        colorMap.put("brown", new Color(139, 69, 19).getRGB());
        colorMap.put("green", Color.GREEN.getRGB());
        colorMap.put("purple", new Color(128, 0, 128).getRGB());
        colorMap.put("red", Color.RED.getRGB());
        colorMap.put("white", Color.WHITE.getRGB());
        colorMap.put("yellow", Color.YELLOW.getRGB());
        colorMap.put("gray", new Color(128, 128, 128).getRGB());
        colorMap.put("orange", new Color(255, 165, 0).getRGB());
        colorMap.put("pink", new Color(255, 192, 203).getRGB());

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
            addEdge(city1, city2);


            PathBlock[] pathBlocks = new PathBlock[blockCount];
            for (int i = 0; i < blockCount; i++) {
                line = br.readLine();
                st = new StringTokenizer(line);
                pathBlocks[i] = new PathBlock(
                    new Color(colorMap.get(st.nextToken())),
                    st.nextToken(),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Double.parseDouble(st.nextToken())
                );
            }
            paths.add(new Path(pathBlocks, getCity(city1), getCity(city2)));
            br.readLine();
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

    public void addEdge(String c1, String c2){
        City city1 = getCity(c1);
        City city2 = getCity(c2);
        city1.getNeighbors().add(city2);
        city2.getNeighbors().add(city1);
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
