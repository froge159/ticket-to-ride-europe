package panels;

import java.awt.Graphics;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.LongPathCard;
import models.NormalPathCard;
import models.Player;
import models.TTRMap;
import models.TrainCard;
import utils.ColorEnum;
import java.io.*;

public class GamePanel extends JPanel {
    private ArrayList<TrainCard> trainCards, discard;
    private ArrayList<NormalPathCard> pathCards;
    private ArrayList<LongPathCard> longCards;
    private Player[] players;
    private int turn;
    private TTRMap map;
    private PlayerPanel playerPanel;
    private JButton confirm, cancel, station, endGame;
    
    public GamePanel() throws IOException {
        String[] temp = {"black", "blue", "brown", "green", "purple", "red", "white", "yellow", "wild"};

        trainCards = new ArrayList<>();
        discard = new ArrayList<>();
        pathCards = new ArrayList<>();
        longCards = new ArrayList<>();
        players = new Player[4];
        turn = 0;
        map = new TTRMap();
        playerPanel = new PlayerPanel();
        confirm = new JButton();
        cancel = new JButton();
        station = new JButton();
        endGame = new JButton();

        for(String i : temp){
            for(int j = 0; j < 12; j++) trainCards.add(new TrainCard(i));
        }
        trainCards.add(new TrainCard("wild"));
        trainCards.add(new TrainCard("wild"));

        BufferedReader br = new BufferedReader(new FileReader("assets/data/routeCards.txt"));
        
        String line;
        while((line = br.readLine()) != null){
            StringTokenizer st2 = new StringTokenizer(line);
            String city1 = st2.nextToken();
            String city2 = st2.nextToken();
            int points = Integer.parseInt(st2.nextToken());
            longCards.add(new LongPathCard(city1, city2, points));
        }

        br.readLine(); // Skip the first line

        while((line = br.readLine()) != null){
            StringTokenizer st2 = new StringTokenizer(line);
            String city1 = st2.nextToken();
            String city2 = st2.nextToken();
            int points = Integer.parseInt(st2.nextToken());
            pathCards.add(new NormalPathCard(city1, city2, points));
        }

        /*File folder = new File("assets/routes/");
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (files != null) {
        for (File file : files) {
            String l = file.getName();
            pathCards.add(new NormalPathCard(ImageIO.read(file)));
            // You can load or process the PNG file here
        }
        } else {
            System.out.println("No PNG files found or directory does not exist.");
        } */


        //shuffle decks
        Collections.shuffle(trainCards);
        Collections.shuffle(pathCards);
        Collections.shuffle(longCards);

        setBackground(ColorEnum.YELLOW.getColor());
        setup();
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        
    }

    public void calculate(){

    }

    public void setup(){
        //setup game
        //deal cards, set up players, etc.
        players = new Player[4];
        players[0] = new Player("red");
        players[1] = new Player("yellow");
        players[2] = new Player("green");
        players[3] = new Player("blue");
        for(int i = 0; i < players.length; i++){
            //setup each player
        }
        playerPanel.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            revalidate();
        });

    }
}
