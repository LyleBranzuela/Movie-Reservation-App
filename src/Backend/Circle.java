/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 * A child of the class shape. A class that is used to handle and create circles.
 * @author lyleb
 */
public class Circle extends Shape implements EnclosesRegion, Serializable
{
   private boolean filled;
   
    /**
     * Default constructor for setting the start point to null.
     */
    public Circle()
    {
        super();
    }

    /**
     * Specifying the start point of a circle.
     * @param startPoint coordinates on where to start
     */
    public Circle(Point startPoint)
    {
        super(startPoint);
    }
    
    /**
     * Method to draw a circle.
     * @param g graphics from a drawing panel.
     */
    @Override
    public void draw(Graphics g)
    {
        g.setColor(super.getColour());
        int radius = Math.abs((super.controlPoint.x - super.startPoint.x) + (super.controlPoint.y - super.startPoint.y));
        // Center of the Circle    
        int x = super.startPoint.x -(radius/2);
        int y = super.startPoint.y - (radius/2);
        if (this.filled)
        {
            g.fillOval(x, y, radius, radius);
        }
        else
        {
           g.drawOval(x, y, radius, radius);
        }
    }

    /**
     * Method to set a circle to be filled or not.
     * @param filled boolean on whether it's filled or not.
     */
    @Override
    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }
}
