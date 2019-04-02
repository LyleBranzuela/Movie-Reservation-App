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
 * A child of the class shape. A class that is used to handle and create Line drawings.
 * @author lyleb
 */
public class Line extends Shape implements Serializable
{
    /**
     * Default constructor for setting the start point to null.
     * @param startPoint coordinates on where to start
     */
    public Line(Point startPoint)
    {
        super(startPoint);
    }
    
    /**
     * Method to draw a line.
     * @param g graphics from a drawing panel.
     */
    @Override
    public void draw(Graphics g)
    {
        g.setColor(super.getColour());
        g.drawLine(super.startPoint.x, super.startPoint.y, super.controlPoint.x, super.controlPoint.y);
    }
    
}
