package engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.MouseEvent;

import panels.ButtonPanel;
import panels.DrawPanel;
import panels.HandPanel;
import panels.MapPanel;
import panels.PlayerPanel;



public class GameEngine {
    private ButtonPanel buttonPanel;
    private DrawPanel drawPanel;
    private HandPanel handPanel;
    private MapPanel mapPanel;
    private PlayerPanel playerPanel;
    private GameEngine engine;

    public GameEngine(ButtonPanel b, DrawPanel d, HandPanel h, MapPanel m, PlayerPanel p) {
        buttonPanel = b;
        drawPanel = d;
        handPanel = h;
        mapPanel = m;
        playerPanel = p;
    }

    
    public void handleMouseClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("\n" + x + " " + y);

        // Check if the click is within the bounds of a station
        // Example: if (x >= stationX && x <= stationX + stationWidth && y >= stationY && y <= stationY + stationHeight) {
        //     // Handle station click
        // }
    }
}
