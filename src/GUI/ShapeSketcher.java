/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Backend.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * The front end GUI of the ShpaeSketcher java, the GUI is used to sketch and draw shapes from its Backend classes.
 * @author lyleb
 */
public class ShapeSketcher extends JPanel implements ActionListener
{
    private JRadioButton freeHandButton, circleButton, lineButton, ovalButton, rectangleButton, squareButton;
    private JCheckBox fillButton;
    private JMenuBar menuBar;
    private JMenu menu;
    private TitledBorder title;
    private JMenuItem openMenuItem, saveMenuItem, resetMenuItem, exitMenuItem;
    private JButton thicknessUpButton, thicknessDownButton, switchColorButton, undoButton, clearButton;
    private DrawingPanel drawingPanel;
    protected JLabel freeHandSizeLabel;
    protected String toolTip;
    protected ArrayList<Shape> shapeArray;
    protected Shape tempShape;
    protected int freeHandSize;
    // Design Fields
    protected Color primaryColor, secondaryColor, tertiaryColor, drawColor, drawBackgroundColor;
    protected Border raisedBevel, loweredBevel, lineBorder;
    protected Point startPoint, controlPoint;
   
    /**
     * Constructor for initializing, designing, and creating the GUI. 
     * The constructor also sets things like the color scheme, size, and borders.
     */
    public ShapeSketcher()
    {
        super(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        this.shapeArray = new ArrayList();
        this.tempShape = null;
        this.freeHandSize = 3;
        this.toolTip = "";
        this.startPoint = new Point(0,0);
        this.controlPoint = new Point(0,0);
        
        // Setting up the Colours
        this.drawColor = new Color(35, 31, 32); // Default Color (BLACK)
        this.primaryColor = new Color(35, 31, 32); // Black
        this.secondaryColor = new Color(241, 89, 42); // Orange
        this.tertiaryColor = new Color(254, 252, 251); // White
        this.drawBackgroundColor = Color.WHITE;

        // Setting up the Border Styles
        this.raisedBevel = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        this.loweredBevel = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        this.lineBorder = BorderFactory.createLineBorder(this.primaryColor, 1);

        // Initializing the Drawing Panel
        this.drawingPanel = new DrawingPanel();
        this.drawingPanel.setBackground(this.tertiaryColor);
        this.drawingPanel.addMouseListener(new DrawingListener());
        this.drawingPanel.addMouseMotionListener(new DrawingListener());

        // Create the menu bar.
        this.menuBar = new JMenuBar();
        this.menuBar.setBackground(this.primaryColor);
        this.menuBar.setBorder(this.raisedBevel);
        
        // Creating the Menu
        this.menu = new JMenu("File");
        this.menu.setMnemonic(KeyEvent.VK_A);
        this.menu.setFont(new Font("Tahoma", Font.BOLD, 14));
        this.menu.setPreferredSize(new Dimension(35, 30));
        this.menu.setForeground(this.tertiaryColor);
        this.menu.getAccessibleContext().setAccessibleDescription("Menu for File Items");
        this.menuBar.add(this.menu);
        
        // Adding all JMenuItems
        this.openMenuItem = new JMenuItem("Open Sketch", KeyEvent.VK_T);
        this.openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        this.openMenuItem.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.openMenuItem.getAccessibleContext().setAccessibleDescription("Open a Sketch File");
        this.openMenuItem.addActionListener(this);
        this.menu.add(this.openMenuItem);
        
        this.saveMenuItem = new JMenuItem("Save Sketch", KeyEvent.VK_T);
        this.saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        this.saveMenuItem.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.saveMenuItem.getAccessibleContext().setAccessibleDescription("Saves The Sketch Made into A File");
        this.saveMenuItem.addActionListener(this);
        this.menu.add(this.saveMenuItem);
        
        this.resetMenuItem = new JMenuItem("Reset to Default", KeyEvent.VK_T);
        this.resetMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        this.resetMenuItem.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.resetMenuItem.getAccessibleContext().setAccessibleDescription("Resets into the default setting");
        this.resetMenuItem.addActionListener(this);
        this.menu.add(this.resetMenuItem);
        
        this.exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_T);
        this.exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        this.exitMenuItem.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.exitMenuItem.getAccessibleContext().setAccessibleDescription("Exits the GUI");
        this.exitMenuItem.addActionListener(this);
        this.menu.add(this.exitMenuItem);
        
        // Creating all the Buttons
        String info = ("Current Free Hand Size: " + freeHandSize);
        this.freeHandSizeLabel = new JLabel(info);
        this.freeHandSizeLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.freeHandSizeLabel.setBackground(this.secondaryColor);
        this.freeHandSizeLabel.setForeground(this.tertiaryColor);
        
        this.thicknessUpButton = new JButton("+");
        this.thicknessUpButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.thicknessUpButton.setBackground(this.secondaryColor);
        this.thicknessUpButton.setForeground(this.tertiaryColor);
        this.thicknessUpButton.addActionListener(this);
        
        this.thicknessDownButton = new JButton("-");
        this.thicknessDownButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.thicknessDownButton.setBackground(this.secondaryColor);
        this.thicknessDownButton.setForeground(this.tertiaryColor);
        this.thicknessDownButton.addActionListener(this);
        
        this.switchColorButton = new JButton("Color");
        this.switchColorButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.switchColorButton.setBackground(this.secondaryColor);
        this.switchColorButton.setForeground(this.tertiaryColor);
        this.switchColorButton.addActionListener(this);
       
        this.undoButton = new JButton("Undo");
        this.undoButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.undoButton.setBackground(this.tertiaryColor);
        this.undoButton.setForeground(this.secondaryColor);
        this.undoButton.addActionListener(this);

        this.clearButton = new JButton("Clear");
        this.clearButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.clearButton.setBackground(this.tertiaryColor);
        this.clearButton.setForeground(this.secondaryColor);
        this.clearButton.addActionListener(this); 
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.thicknessUpButton);
        buttonGroup.add(this.thicknessDownButton);
        buttonGroup.add(this.switchColorButton);
        buttonGroup.add(this.undoButton);
        buttonGroup.add(this.clearButton);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(this.primaryColor);
        buttonPanel.add(this.freeHandSizeLabel);
        buttonPanel.add(this.thicknessUpButton);
        buttonPanel.add(this.thicknessDownButton);
        buttonPanel.add(this.switchColorButton);
        buttonPanel.add(this.undoButton);
        buttonPanel.add(this.clearButton);
        
        // Creating all the Drawing Buttons
        this.fillButton = new JCheckBox("Fill");
        this.fillButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.fillButton.setBackground(this.primaryColor);
        this.fillButton.setForeground(this.tertiaryColor);
        this.fillButton.setEnabled(false);
        this.fillButton.setPreferredSize(new Dimension(90, this.fillButton.getMinimumSize().height));
        this.fillButton.setMaximumSize(new Dimension(100, this.fillButton.getMinimumSize().height));
        this.fillButton.addActionListener(this);

        this.freeHandButton = new JRadioButton("Freehand");
        this.freeHandButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.freeHandButton.setBackground(this.tertiaryColor);
        this.freeHandButton.setForeground(this.secondaryColor);
        this.freeHandButton.setPreferredSize(new Dimension(90, this.fillButton.getMinimumSize().height));
        this.freeHandButton.setMaximumSize(new Dimension(100, this.fillButton.getMinimumSize().height));
        this.freeHandButton.setBorder(this.lineBorder);
        this.freeHandButton.addActionListener(this);

        this.circleButton = new JRadioButton("Circle");
        this.circleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.circleButton.setBackground(this.tertiaryColor);
        this.circleButton.setForeground(this.secondaryColor);
        this.circleButton.setPreferredSize(new Dimension(90, this.circleButton.getMinimumSize().height));
        this.circleButton.setMaximumSize(new Dimension(120, this.circleButton.getMinimumSize().height));
        this.circleButton.setBorder(this.lineBorder);
        this.circleButton.addActionListener(this);
        
        this.lineButton = new JRadioButton("Line");
        this.lineButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.lineButton.setBackground(this.tertiaryColor);
        this.lineButton.setForeground(this.secondaryColor);
        this.lineButton.setPreferredSize(new Dimension(90, this.lineButton.getMinimumSize().height));
        this.lineButton.setMaximumSize(new Dimension(120, this.lineButton.getMinimumSize().height));
        this.lineButton.setBorder(this.lineBorder);
        this.lineButton.addActionListener(this);

        this.ovalButton = new JRadioButton("Oval");
        this.ovalButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.ovalButton.setBackground(this.tertiaryColor);
        this.ovalButton.setForeground(this.secondaryColor);
        this.ovalButton.setPreferredSize(new Dimension(90, this.ovalButton.getMinimumSize().height));
        this.ovalButton.setMaximumSize(new Dimension(120, this.ovalButton.getMinimumSize().height));
        this.ovalButton.setBorder(this.lineBorder);
        this.ovalButton.addActionListener(this);

        this.rectangleButton = new JRadioButton("Rectangle");
        this.rectangleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.rectangleButton.setBackground(this.tertiaryColor);
        this.rectangleButton.setForeground(this.secondaryColor);
        this.rectangleButton.setPreferredSize(new Dimension(90, this.rectangleButton.getMinimumSize().height));
        this.rectangleButton.setMaximumSize(new Dimension(90, this.rectangleButton.getMinimumSize().height));
        this.rectangleButton.setBorder(this.lineBorder);
        this.rectangleButton.addActionListener(this);

        this.squareButton = new JRadioButton("Square");
        this.squareButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.squareButton.setBackground(this.tertiaryColor);
        this.squareButton.setForeground(this.secondaryColor);
        this.squareButton.setPreferredSize(new Dimension(90, this.squareButton.getMinimumSize().height));
        this.squareButton.setMaximumSize(new Dimension(90, this.squareButton.getMinimumSize().height));
        this.squareButton.setBorder(this.lineBorder);
        this.squareButton.addActionListener(this);
        
        ButtonGroup drawButtonGroup = new ButtonGroup();
        drawButtonGroup.add(this.freeHandButton);
        this.freeHandButton.setSelected(true);
        drawButtonGroup.add(this.circleButton);
        drawButtonGroup.add(this.lineButton);
        drawButtonGroup.add(this.ovalButton);
        drawButtonGroup.add(this.rectangleButton);
        drawButtonGroup.add(this.squareButton);

        JPanel drawButtonPanel = new JPanel();
        // Fill Button is separated so that it can be used together with the radio buttons
        drawButtonPanel.setLayout(new BoxLayout(drawButtonPanel, BoxLayout.PAGE_AXIS));
        drawButtonPanel.setBackground(this.secondaryColor);
        drawButtonPanel.setBorder(BorderFactory.createLineBorder(this.primaryColor, 3));
                this.title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(this.primaryColor, 4),
                "Design", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 18), this.primaryColor);
        drawButtonPanel.setBorder(this.title);
        drawButtonPanel.add(this.fillButton);
        drawButtonPanel.add(this.freeHandButton);
        drawButtonPanel.add(this.circleButton);
        drawButtonPanel.add(this.lineButton);
        drawButtonPanel.add(this.ovalButton);
        drawButtonPanel.add(this.rectangleButton);
        drawButtonPanel.add(this.squareButton);
        
        //Add the buttonPanel
        add(this.menuBar, BorderLayout.NORTH);
        add(drawButtonPanel, BorderLayout.WEST);
        add(this.drawingPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Loading a saved Drawing File to the Drawing Panel
     * @param f file pointer that was selected by the user.
     * @throws IOException exceptions produced by failed or interrupted I/O operations.
     * @throws ClassNotFoundException no definition for the class with the specified name could be found.
     */
    public void loadDrawingFile(File f) throws IOException, ClassNotFoundException
    {
        ArrayList<Shape> list = new ArrayList();
        Shape tempSketch = null;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        int size = ois.readInt();
        for (int counter = 0; counter < size; counter++)
        {
            tempSketch = (Shape) ois.readObject();
            list.add(tempSketch);
        }
        ois.close();
        this.shapeArray = list;
    }
    
    /**
     * Saving the current sketch into a specified file directory.
     * @param list specified array list of data to be saved by the program.
     * @param f file pointer that was selected by the user
     * @throws IOException exceptions produced by failed or interrupted I/O operations.
     */
    public void saveDrawingFile(ArrayList<Shape> list, File f) throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeInt(list.size());
        for (Shape shapes : list)
        {
            oos.writeObject(shapes);
        }
        oos.flush();
        oos.close();
    }

    /**
     * Action listener for when the button is pressed, and how they should react.
     * @param e event that is called.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        // Switches the color of the blobs
        if (this.freeHandButton.isSelected())
        {
            this.fillButton.setEnabled(false);
            this.thicknessUpButton.setEnabled(true);
            this.thicknessDownButton.setEnabled(true);
        }
        else
        {
            this.fillButton.setEnabled(true);
            this.thicknessUpButton.setEnabled(false);
            this.thicknessDownButton.setEnabled(false);
        }
        // Increases the thickness of the free hand sketch
        if (source == this.thicknessUpButton)
        {
            if (this.freeHandSize < 10)
            {
                this.freeHandSize++;
                String info = ("Current Free Hand Size: " + freeHandSize);
                this.freeHandSizeLabel.setText(info);
                repaint();
            }
            else
            {
                this.thicknessUpButton.setEnabled(false);
            }
        }
        // Decreases the thickness of the free hand sketch
        else if (source == this.thicknessDownButton)
        {
            if (this.freeHandSize > 0)
            {
                this.freeHandSize--;
                String info = ("Current Free Hand Size: " + freeHandSize);
                this.freeHandSizeLabel.setText(info);
                repaint();
            }
            else
            {
                this.thicknessDownButton.setEnabled(false);
            }
        }
        // Switches the color of the sketch shapes
        else if (source == this.switchColorButton)
        {
            Color c = JColorChooser.showDialog(null, "Choose a Color", this.drawColor);
            if (c != null)
            {
                this.drawColor = c;
            }
            JColorChooser colorChooser = new JColorChooser(this.drawColor);
            this.drawColor = colorChooser.getColor();
        }
        // Undo the last drawn sketch
        else if (source == this.undoButton)
        {
            if (!this.shapeArray.isEmpty())
            {
                this.shapeArray.remove(this.shapeArray.size() - 1);
                tempShape = null;
                repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "NO MORE SKETCHES TO UNDO !!", "Undo Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Clearing the Current Sketch in the Drawing Panel
        else if (source == this.clearButton)
        {
            this.shapeArray.clear();
            tempShape = null;
            repaint();
        }
        // Opening a Drawing File
        else if (source == this.openMenuItem)
        {
            JFileChooser chooser = new JFileChooser(new File("."));
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    loadDrawingFile(chooser.getSelectedFile());
                }
                catch (IOException error)
                {
                    JOptionPane.showMessageDialog(null, error, "ERROR LOADING FILE", JOptionPane.ERROR_MESSAGE);
                }
                catch (ClassNotFoundException ex)
                {
                    JOptionPane.showMessageDialog(null, ex, "ERROR WITH OBJECT CAST", JOptionPane.ERROR_MESSAGE);
                }
            }
            repaint();
        }
        // Saving the Drawing to a File
        else if (source == this.saveMenuItem)
        {
            JFileChooser chooser = new JFileChooser(new File("."));
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    saveDrawingFile(this.shapeArray, chooser.getSelectedFile());
                }
                catch (IOException error)
                {
                    JOptionPane.showMessageDialog(null, error, "ERROR SAVING FILE", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        // Reset the settings to a Default
        else if (source == this.resetMenuItem)
        {
            this.shapeArray.clear();
            this.tempShape = null;
            this.drawBackgroundColor = Color.WHITE;
            this.drawColor = Color.BLACK;
            this.freeHandSize = 3;
            repaint();
        }
        // Exits the GUI
        else if (source == this.exitMenuItem)
        {
            System.exit(0);
        }
    }
    
   /**
     * A separate JPanel meant to handle all the painting and drawing of the component.
     */
    private class DrawingPanel extends JPanel
    {
        /**
         * Default constructor for setting up the settings of the drawing panel.
         */
        public DrawingPanel()
        {
            setSize(new Dimension(800, 800));
        }

        /**
         * Draws all the components of the drawing panel
         * @param g specifies the current graphic space the panel is using.
         */
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
           
            if (!shapeArray.isEmpty())
            {
                int sketchNum = shapeArray.size();
                for (int i = 0; i < sketchNum; i++)
                {
                    shapeArray.get(i).draw(g);
                }
            }

            if (tempShape != null)
            {
                g.setFont(new Font("Tahoma", Font.BOLD, 12));
                g.drawString(toolTip, controlPoint.x + 4, controlPoint.y + 4);
                tempShape.draw(g);
            }
        }
    }
    
   /**
    *  A listener class for listening to all mouse motion or mouse events that happens.
    */
   private class DrawingListener implements MouseMotionListener, MouseListener 
   {
       private Point previousPoint;
       
       /**
        * Default Constructor to initialize the Previous Point
        */
       public DrawingListener()
       {
           previousPoint = new Point(0,0);
       }
       
       /**
        * Mouse listener that detects whether a mouse is pressed or not.
        * If left click is pressed, the point where it is clicked is saved and becomes the start point.
        * @param e mouse event the user interacts (pressing any mouse buttons)
        */
       @Override
       public void mousePressed(MouseEvent e)
       {
           if (SwingUtilities.isLeftMouseButton(e))
           {
               startPoint = e.getPoint();
               previousPoint = startPoint;
           }
       }
       
       /**
        * A motion mouse listener that listens on how the user changes the control point.
        * If left click is dragged, the control point gets updated.
        * @param e mouse event the user interacts (dragging a click)
        */
       @Override
       public void mouseDragged(MouseEvent e)
       {
           if (SwingUtilities.isLeftMouseButton(e))
           {
                controlPoint = e.getPoint();
                if (controlPoint.x > previousPoint.x && controlPoint.y == previousPoint.y)
                {
                    toolTip = "H";
                }
                else if (controlPoint.y > previousPoint.y && controlPoint.x == previousPoint.x)
                {
                    toolTip = "V";
                }
                else
                {
                    toolTip = "";
                }
                this.previousPoint = controlPoint;
                // Freehand Version
                if (freeHandButton.isSelected())
                {
                    Freehand tempFreehand = new Freehand(controlPoint);
                    tempFreehand.setSize(freeHandSize);
                    tempFreehand.setColour(drawColor);
                    shapeArray.add(tempFreehand);
                }
                // Circle Version
                else if (circleButton.isSelected())
                {
                    Circle tempCircle = new Circle(startPoint);
                    tempCircle.setFilled(fillButton.isSelected());
                    tempShape = tempCircle;
                }
                // Line Version
                else if (lineButton.isSelected())
                {
                    tempShape = new Line(startPoint);
                }
                // Oval Version
                else if (ovalButton.isSelected())
                {
                    Oval tempOval = new Oval(startPoint);
                    tempOval.setFilled(fillButton.isSelected());
                    tempShape = tempOval;
                }
                // Rectangle Version
                else if (rectangleButton.isSelected())
                {
                    Rectangle tempRectangle = new Rectangle(startPoint);
                    tempRectangle.setFilled(fillButton.isSelected());
                    tempShape = tempRectangle;
                }
                // Square Version
                else if (squareButton.isSelected())
                {
                    Square tempSquare = new Square(startPoint);
                    tempSquare.setFilled(fillButton.isSelected());
                    tempShape = tempSquare;
                }
                // Check if there's any shapes (So that Freehand can be used)
                if (tempShape != null)
                {
                    tempShape.setControlPoint(controlPoint);
                    tempShape.setColour(drawColor);
                }
                repaint();
           }
       }

       /**
        * A mouse listener that listens when a mouse is released from being held down.
        * @param e mouse event the user interacts (releasing the mouse button)
        */
       @Override
       public void mouseReleased(MouseEvent e)
       {
           if (SwingUtilities.isLeftMouseButton(e))
           {
                int button = e.getButton();
                if (button == MouseEvent.BUTTON1 && !freeHandButton.isSelected())
                {
                    shapeArray.add(tempShape);
                    tempShape = null;
                    repaint();
                }
           }
       }
       
       /**
        * Unused Mouse Motion Listener
        * @param e mouse event the user interacts (moving the mouse)
        */
       @Override
       public void mouseMoved(MouseEvent e){}

       /**
        * Unused Mouse Listener
        * @param e mouse event the user interacts (dragging a click)
        */
       @Override
       public void mouseClicked(MouseEvent e){}

       /**
        * Unused Mouse Motion Listener
        * @param e mouse event the user interacts (mouse enters the area)
        */
       @Override
       public void mouseEntered(MouseEvent e){}

       /**
        * Unused Mouse Motion Listener
        * @param e mouse event the user interacts (mouse exits the area)
        */
       @Override
       public void mouseExited(MouseEvent e){}
    }
 
    public static void main(String[] args)
    {
        ShapeSketcher myPanel = new ShapeSketcher();
        JFrame frame = new JFrame("Shape Sketcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(myPanel);
        frame.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(new Point((d.width / 2) - (frame.getWidth() / 2), (d.height / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }
}
