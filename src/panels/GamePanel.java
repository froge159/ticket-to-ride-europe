package panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;

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
import utils.ImageEnum;
import utils.Rel;
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

        players = new Player[4]; // init players
        players[0] = new Player("red");
        players[1] = new Player("yellow");
        players[2] = new Player("green");
        players[3] = new Player("blue");

        for (String i : temp) { // init traincards
            for (int j = 0; j < 12; j++)
                trainCards.add(new TrainCard(i));
        }
        trainCards.add(new TrainCard("wild"));
        trainCards.add(new TrainCard("wild"));

        BufferedReader br = new BufferedReader(new FileReader("assets/data/routeCards.txt"));

        String line;
        while (!(line = br.readLine()).equals("")) {
            StringTokenizer st2 = new StringTokenizer(line);
            String city1 = st2.nextToken();
            String city2 = st2.nextToken();
            int points = Integer.parseInt(st2.nextToken());
            longCards.add(new LongPathCard(city1, city2, points));
        }

        br.readLine(); // Skip the first line

        while ((line = br.readLine()) != null) {
            StringTokenizer st2 = new StringTokenizer(line);
            String city1 = st2.nextToken();
            String city2 = st2.nextToken();
            int points = Integer.parseInt(st2.nextToken());
            pathCards.add(new NormalPathCard(city1, city2, points));
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
        HandPanel hp = new HandPanel();
        MapPanel mp = new MapPanel();

        GameEngine ge = new GameEngine(bp, dp, hp, mp, pp);
        GameController gc = new GameController(bp, dp, hp, mp, pp, this, se, ge);

        pp.setBounds(Rel.X(1730), Rel.Y(20), pp.getWidth(), pp.getHeight());
        bp.setBounds(Rel.X(1680), Rel.Y(550), bp.getWidth(), bp.getHeight());
        mp.setBounds(Rel.X(20), Rel.Y(0), mp.getWidth(), mp.getHeight());

        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            add(pp);
            add(bp);
            add(mp);
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
