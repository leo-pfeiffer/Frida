package view;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import javax.swing.JPanel;

import java.util.Random;

public class DrawPanel extends JPanel {

    /** X coordinate from where to draw the line. */
    private int startX;
    /** Y coordinate from where to draw the line. */
    private int startY;

    /** X coordinate to where to draw the line. */
    private int endX;
    /** Y coordinate to where to draw the line. */
    private int endY;

    /** Contains all shapes that are currently painted. */
    private ArrayList<Shape> shapes;
    /** Contains undone shapes. */
    private ArrayList<Shape> undoneShapes;

    /** Contains all colours corresponding to the shapes. */
    private ArrayList<Color> colours;
    /** Contains colours of undone shapes. */
    private ArrayList<Color> undoneColours;


    public DrawPanel() {
        shapes = new ArrayList<Shape>();
        undoneShapes = new ArrayList<Shape>();

        colours = new ArrayList<Color>();
        undoneColours = new ArrayList<Color>();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int i=0; i < shapes.size(); i++) {
            // Get the shape and colour that go together
            Shape shape = shapes.get(i);
            Color colour = colours.get(i);

            // Set colour and draw the shape.
            g2d.setColor(colour);
            g2d.draw(shape);
        }
    }

    public void setArguments(int startX, int startY, int endX, int endY,
                             Color colour) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        addLine();
        addColour(colour);
    }

    /** Add a new line to the panel. */
    public void addLine() {
        Line2D line = new Line2D.Double(startX, startY, endX, endY);
        shapes.add(line);
    }

    /** Add the colour of the new shape to the corresponding list.
     * @param colour The colour to be added. */
    public void addColour(Color colour) {
        colours.add(colour);
    }

    public Shape getLastShape(ArrayList<Shape> s) {
        try {
            return s.get(s.size() - 1);
        } catch(IndexOutOfBoundsException ioobe) {
            return null;
        }
    }

    public Color getLastColour(ArrayList<Color> c) {
        try {
            return c.get(c.size() - 1);
        } catch(IndexOutOfBoundsException ioobe) {
            return null;
        }
    }

    /** Clear all array lists that contain graphics. */
    public void clearAll() {
        shapes.clear();
    }

    /** Remove last Shape. */
    public void undo() {
        Shape s = getLastShape(shapes);
        Color c = getLastColour(colours);
        if (s == null & c == null) {
            System.err.println("Nothing to undo");
        } else {
            // handle shapes
            undoneShapes.add(s);
            shapes.remove(s);

            // handle colours
            undoneColours.add(c);
            colours.remove(c);
        }
    }

    /** Redo the last removed shape. */
    public void redo() {
        Shape s = getLastShape(undoneShapes);
        Color c = getLastColour(colours);
        if (s == null & c == null) {
            System.err.println("Nothing to redo");
        } else {
            // handle shape
            undoneShapes.remove(s);
            shapes.add(s);

            // handle colours
            undoneColours.remove(c);
            colours.add(c);
        }
    }
}
