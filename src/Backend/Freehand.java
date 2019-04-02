package Backend;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A child of the class shape. A class that is used to handle and create Freehand drawings.
 * @author lyleb
 */
public class Freehand extends Shape implements Serializable
{
    private int size;
    
    /**
     * Default constructor for setting the start point to null.
     */
    public Freehand()
    {
        super();
        this.size = 3;
    }

    /**
     * Specifying the start point of the Freehand.
     * @param startPoint coordinates on where to start
     */
    public Freehand(Point startPoint)
    {
        super(startPoint);
        this.size = 3;
    }
    
    /**
     * Method to set the size of the Freehand blobs.
     * @param size amount of size.
     */
    public void setSize(int size)
    {
       this.size = size;
    }
    
    /**
     * Method to draw the freehand style.
     * @param g graphics from a drawing panel.
     */
    @Override
    public void draw(Graphics g)
    {
        g.setColor(super.getColour());
        g.fillOval(super.startPoint.x - 4, super.startPoint.y - 4, this.size, this.size);
    }
    
}
