/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author lyleb
 */
public abstract class Shape implements Serializable
{
    protected Point startPoint;
    protected Point controlPoint;
    private Color colour;
    
    /**
     * Default constructor for setting the start point to null.
     */
    public Shape()
    {
        this.startPoint = null;
    }
 
    /**
     * Specifying the start point of a shape.
     * @param startPoint coordinates on where to start
     */
    public Shape(Point startPoint)
    {
        this.startPoint = startPoint;
    }
    
    /**
     * Method to get the color of the shape.
     * @return 
     */
    public Color getColour()
    {
        return this.colour;
    }
    
    /**
     * 
     * @param colour 
     */
    public void setColour(Color colour)
    {
        this.colour = colour;
    }
    
    /**
     * 
     * @param controlPoint 
     */
    public void setControlPoint(Point controlPoint)
    {
        this.controlPoint = controlPoint;
    }
    
    /**
     * 
     * @param g 
     */
    public abstract void draw(Graphics g);
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        return "Shapes";
    }
}
