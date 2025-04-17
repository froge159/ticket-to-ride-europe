package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class PathBlock {
    private Color color;
    String type;
    private int deg, x, y;

    public PathBlock(Color col, String ty, int d, int x, int y){
        color = col;
        type = ty;
        deg = d;
        this.x = x;
        this.y = y;
    }

    public boolean contains(Point p) {
        // Get rectangle center
        double centerX = x + 48/2.0;
        double centerY = y + 14/2.0;
        
        // Create inverse transform to un-rotate the point
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(-deg), centerX, centerY);
        Point2D.Double transformedPoint = new Point2D.Double(p.x, p.y);
        transform.transform(transformedPoint, transformedPoint);
        
        // Check if un-rotated point is inside original rectangle
        return transformedPoint.x >= x && 
               transformedPoint.x <= x + 48 &&
               transformedPoint.y >= y && 
               transformedPoint.y <= y + 14;
    }
    
    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        g2d.rotate(Math.toRadians(deg), x + 48/2.0, y + 14/2.0);
        g2d.setColor(color);
        g2d.fillRect(x, y, 48, 14);
        g2d.setTransform(oldTransform);
    }


    public Color getColor(){return color;}
    public String getType(){return type;}
    public int[] getPos(){return new int[]{x, y, deg};}
}
