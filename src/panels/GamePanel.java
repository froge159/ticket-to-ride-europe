package panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controllers.GameController;
import engine.GameEngine;
import engine.StartEngine;
import models.LongPathCard;
import models.NormalPathCard;
import models.Player;
import models.TTRMap;
import models.TrainCard;
import utils.CardImages;
import utils.ImageEnum;
import utils.Rel;
import java.io.*;

public class GamePanel extends JPanel {

    private ArrayList<TrainCard> trainCards, discard;
    private ArrayList<NormalPathCard> pathCards;
    private ArrayList<LongPathCard> longCards;
    private Player[] players;
    private HandPanel[] handPanels;
    private int turn;
    private TTRMap map;
    private PlayerPanel playerPanel;
    private JButton confirm, cancel, station, endGame;
    private BufferedImage gameBG;
    private StartEngine se;

    public GamePanel() throws IOException {
        String[] temp = { "black", "blue", "brown", "green", "purple", "red", "white", "yellow", "wild" };

        trainCards = new ArrayList<>();
        discard = new ArrayList<>();
        pathCards = new ArrayList<>();
        longCards = new ArrayList<>();
        players = new Player[4];
        turn = 0;
        map = new TTRMap();
        confirm = new JButton();
        cancel = new JButton();
        station = new JButton();
        endGame = new JButton();

        players = new Player[4]; 
        players[0] = new Player("red");
        players[1] = new Player("yellow");
        players[2] = new Player("green");
        players[3] = new Player("blue");

        // load train cards
        for (String i : temp) {
            for (int j = 0; j < 12; j++)
                trainCards.add(new TrainCard(i, false, CardImages.addImage(i + "-train",
                        ImageIO.read(new File("assets/traincards/" + i + "card.png")))));
        }
        trainCards.add(new TrainCard("wild", false,
                CardImages.addImage("wild-train", ImageIO.read(new File("assets/traincards/wildcard.png")))));
        trainCards.add(new TrainCard("wild", false,
                CardImages.addImage("wild-train", ImageIO.read(new File("assets/traincards/wildcard.png")))));

        BufferedReader br = new BufferedReader(new FileReader("assets/data/routeCards.txt"));

        // load long paths
        String line;
        while (!(line = br.readLine()).equals("")) {
            StringTokenizer st2 = new StringTokenizer(line);
            String city1 = st2.nextToken().toLowerCase();
            String city2 = st2.nextToken().toLowerCase();
            int points = Integer.parseInt(st2.nextToken());
            longCards.add(new LongPathCard(city1, city2, points, CardImages.addImage(city1 + "-" + city2 + "-long-path",
                    ImageIO.read(new File("assets/longroutes/" + city1 + "-" + city2 + ".png")))));
        }

        br.readLine(); // Skip the first line   

        // load normal paths
        while ((line = br.readLine()) != null) {
            StringTokenizer st2 = new StringTokenizer(line);
            String city1 = st2.nextToken().toLowerCase();
            String city2 = st2.nextToken().toLowerCase();
            int points = Integer.parseInt(st2.nextToken());
                pathCards.add(new NormalPathCard(city1, city2, points, CardImages.addImage(city1 + "-" + city2 + "-path",
                        ImageIO.read(new File("assets/routes/" + city1 + "-" + city2 + ".png")))));            
        }

        /*
         * File folder = new File("assets/routes/");
         * File[] files = folder.listFiles((dir, name) ->
         * name.toLowerCase().endsWith(".png"));
         * 
         * if (files != null) {
         * for (File file : files) {
         * String l = file.getName();
         * pathCards.add(new NormalPathCard(ImageIO.read(file)));
         * // You can load or process the PNG file here
         * }
         * } else {
         * System.out.println("No PNG files found or directory does not exist.");
         * }
         */

        // shuffle decks
        Collections.shuffle(trainCards);
        Collections.shuffle(pathCards);
        Collections.shuffle(longCards);
    }

    public void calculate() {

    }

    public void initComponents() {
        gameBG = ImageEnum.GAMEBG.getImage();
        PlayerPanel pp = new PlayerPanel(players);
        ButtonPanel bp = new ButtonPanel();
        DrawPanel dp = new DrawPanel();
        MapPanel mp = new MapPanel();
        handPanels = new HandPanel[4];
        handPanels[0] = new HandPanel(players[0]);
        handPanels[1] = new HandPanel(players[1]);
        handPanels[2] = new HandPanel(players[2]);
        handPanels[3] = new HandPanel(players[3]);

        GameEngine ge = new GameEngine(bp, dp, handPanels, mp, pp);
        GameController gc = new GameController(bp, dp, handPanels, mp, pp, this, se, ge);

        pp.setBounds(Rel.X(1730), Rel.Y(20), pp.getWidth(), pp.getHeight());
        bp.setBounds(Rel.X(1680), Rel.Y(550), bp.getWidth(), bp.getHeight());
        mp.setBounds(Rel.X(20), Rel.Y(0), mp.getWidth(), mp.getHeight());
        for (int i = 0; i < 4; i++) {
            handPanels[i].setBounds(Rel.X(20), Rel.Y(890), handPanels[i].getWidth(), handPanels[i].getHeight());
        }

        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            add(pp);
            add(bp);
            add(mp);
            add(handPanels[0]); // assume we start from player 1
            revalidate();
            repaint();
        });
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setStartEngine(StartEngine se) {
        this.se = se;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameBG != null) {
            g.drawImage(gameBG, 0, 0, this);
        }
    }
}
