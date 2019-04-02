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
 * A child of the class shape. A class that is used to handle and create ovals.
 * @author lyleb
 */
public class Oval extends Shape implements EnclosesRegion, Serializable
{
    private boolean filled;
    
    /**
     * Default constructor for setting the start point to null.
     */
    public Oval()
    {
        super();
    }
    
    /**
     * Specifying the start point of a Oval.
     * @param startPoint coordinates on where to start
     */
    public Oval(Point startPoint)
    {
        super(startPoint);
    }
    
    /**
     * Method to draw a Oval.
     * @param g graphics from a drawing panel.
     */
    @Override
    public void draw(Graphics g)
    {
        g.setColor(super.getColour());
        int width = (Math.abs(super.controlPoint.x - super.startPoint.x) + Math.abs(super.controlPoint.x - super.startPoint.x))/2;
        int height = (Math.abs(super.controlPoint.y - super.startPoint.y) + Math.abs(super.controlPoint.y - super.startPoint.y))/2;
        // Center of the Oval
        int x = super.startPoint.x - (width/2);
        int y = super.startPoint.y - (height/2);
        if (this.filled)
        {
            g.fillOval(x, y, width, height);
        }
        else
        {
            g.drawOval(x, y, width, height);
        }
    }

    /**
     * Method to set a Oval to be filled or not.
     * @param filled boolean on whether it's filled or not.
     */
    @Override
    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }
}
