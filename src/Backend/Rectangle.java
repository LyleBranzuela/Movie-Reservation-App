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
 * A child of the class shape. A class that is used to handle and create rectangles.
 * @author lyleb
 */
public class Rectangle extends Shape implements EnclosesRegion, Serializable
{
   private boolean filled;
   
    /**
     * Default constructor for setting the start point to null.
     */
    public Rectangle()
    {
        super();
    }
    
    /**
     * Specifying the start point of a Rectangle.
     * @param startPoint coordinates on where to start
     */
    public Rectangle(Point startPoint)
    {
        super(startPoint);
    }
    
    /**
     * Method to draw a Rectangle.
     * @param g graphics from a drawing panel.
     */
    @Override
    public void draw(Graphics g)
    {
        g.setColor(super.getColour());
        int width = super.controlPoint.x - super.startPoint.x;
        int height = super.controlPoint.y - super.startPoint.y;

        if (this.filled)
        {
            g.fillRect(super.startPoint.x, super.startPoint.y, width, height);
        }
        else
        {
            g.drawRect(super.startPoint.x, super.startPoint.y, width, height);
        }
    }

    /**
     * Method to set a Rectangle to be filled or not.
     * @param filled boolean on whether it's filled or not.
     */
    @Override
    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }
}
