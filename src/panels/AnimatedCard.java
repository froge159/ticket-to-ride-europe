package panels;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import models.PathCard;


public class AnimatedCard extends JPanel {

    private PathCard card;
    private ImageIcon front;
    private Point corner;
    
    public AnimatedCard(PathCard card, int w, int h, int x, int y) {
        this.card = card;
        front = card.getFront();
        corner = new Point(x, y);

        setBounds(x, y, w, h);
        setLayout(null);
        setOpaque(false); // Allow overlapping by making the panel non-opaque
    }

    public void setCorner(Point p) {
        this.corner = p;
    }

    public int getX() {
        return corner.x;
    }
    public int getY() {
        return corner.y;
    }
    public PathCard getCard() {
        return card;
    }
    public ImageIcon getFront() {
        return front;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        front.paintIcon(this, g, 0, 0);
    } 


}
