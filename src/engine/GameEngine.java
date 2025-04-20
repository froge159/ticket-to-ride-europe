package engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Point;



import java.awt.event.MouseEvent;
import java.util.ArrayList;

import panels.ButtonPanel;
import panels.DrawPanel;
import panels.HandPanel;
import panels.MapPanel;
import panels.PlayerPanel;
import utils.Rel;
import panels.AnimatedCard;


public class GameEngine {
    private ButtonPanel buttonPanel;
    private DrawPanel drawPanel;
    private HandPanel[] handPanels;
    private MapPanel mapPanel;
    private PlayerPanel playerPanel;
    private GameEngine engine;

    public GameEngine(ButtonPanel b, DrawPanel d, HandPanel[] h, MapPanel m, PlayerPanel p) {
        buttonPanel = b;
        drawPanel = d;
        handPanels = h;
        mapPanel = m;
        playerPanel = p;
    }

    
    public void handleMouseClick(MouseEvent e) { // print coordinates of mouse click for map (testing)
        int x = e.getX();
        int y = e.getY();
        System.out.println("\n" + x + " " + y);

        // Check if the click is within the bounds of a station
        // Example: if (x >= stationX && x <= stationX + stationWidth && y >= stationY && y <= stationY + stationHeight) {
        //     // Handle station click
        // }
    }

    public void animatePathCardsEnter(HandPanel hp) {
        ArrayList<AnimatedCard> animatedPathCards = hp.getAnimatedPathCards();
        for (int i = 1; i < animatedPathCards.size(); i++) {
            final int index = i; 
            AnimatedCard c = animatedPathCards.get(index);
            animatedPathCards.get(index).setCorner(new Point(Rel.X(c.getX()), Rel.Y(c.getY() + 40 * index - index * 130))); // move cards up
            SwingUtilities.invokeLater(() -> {
                animatedPathCards.get(index).repaint();
                hp.repaint(); hp.revalidate();
            });
            if (!java.util.Arrays.asList(hp.getComponents()).contains(c)) { // if not already in the panel, add it
                SwingUtilities.invokeLater(() -> {
                    hp.add(c);
                    hp.repaint(); hp.revalidate();
                });
            }
        }
    }

    public void animatePathCardsLeave(HandPanel hp) {
        ArrayList<AnimatedCard> animatedPathCards = hp.getAnimatedPathCards();
        for (int i = 2; i < animatedPathCards.size(); i++) { // remove all cards except first two
            AnimatedCard c = animatedPathCards.get(i);
            c.setCorner(new Point(Rel.X(c.getX()), Rel.Y(c.getY() - 40 * i + i * 130))); // move cards down
            if (java.util.Arrays.asList(hp.getComponents()).contains(c)) {
                SwingUtilities.invokeLater(() -> {
                    hp.remove(c);
                    hp.repaint();
                    hp.revalidate();
                });
            }
        }
        animatedPathCards.get(1).setCorner(new Point(Rel.X(1370), Rel.Y(895))); // update position if 2nd card
        SwingUtilities.invokeLater(() -> {
            animatedPathCards.get(1).repaint();
            hp.repaint(); hp.revalidate();
        });
    }

}
