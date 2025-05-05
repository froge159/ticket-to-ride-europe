package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import utils.PNGEnum;
import utils.Rel;

public class PathBlock {

    private Color color;
    private String type;
    private double deg, x, y;
    private double centerX = x + 48/2.0;
    private double centerY = y + 14/2.0;
    private String ownerColor;

    public PathBlock(Color col, String ty, int x, int y, double d){
        color = col;
        type = ty;
        deg = d;
        centerX = x;
        centerY = y;
        ownerColor = null;
        this.x = (centerX - 48/2.0);
        this.y = (centerY - 14/2.0);
    }

    public boolean contains(Point p) {
        
        // Create inverse transform to un-rotate the point
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(-deg), centerX, centerY);
        Point2D.Double transformedPoint = new Point2D.Double(p.x, p.y);
        transform.transform(transformedPoint, transformedPoint);
        
        // Check if un-rotated point is inside original rectangle
        return transformedPoint.x >= x && 
               transformedPoint.x <= x + Rel.X(48) &&
               transformedPoint.y >= y && 
               transformedPoint.y <= y + Rel.Y(14);
    }
    
    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        g2d.rotate(Math.toRadians(-deg), x + 48/2.0, y + 14/2.0);
        //g2d.setColor(color);
        //g2d.fillRect((int)x, (int)y, Rel.W(48), Rel.H(14));
        if (ownerColor != null) {
            g2d.drawImage(PNGEnum.getColoredTrainImage(ownerColor), (int)x, (int)y, Rel.W(48), Rel.H(14), null);
        }
        g2d.setTransform(oldTransform);
    }

    public void setOwnerColor(String c){
        ownerColor = c;
    }

    public Color getColor(){return color;}
    public String getType(){return type;}
    public int[] getPos(){return new int[]{(int)x, (int)y, (int)deg};}
}
