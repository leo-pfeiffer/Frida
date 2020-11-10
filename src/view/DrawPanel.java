package view;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.Color;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {

    /** X coordinate from where to draw the line. */
    private int startX;
    /** Y coordinate from where to draw the line. */
    private int startY;

    /** X coordinate to where to draw the line. */
    private int endX;
    /** Y coordinate to where to draw the line. */
    private int endY;

    /** Current color setting. */
    private Color colour;
    /** Contains all shapes that are currently painted. */
    private ArrayList<Shape> shapes;

    /** Contains undone shapes. */
    private ArrayList<Shape> undoneShapes;


    public DrawPanel() {
        shapes = new ArrayList<Shape>();
        undoneShapes = new ArrayList<Shape>();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // todo make this s.t. color only changes for new shapes
        g2d.setColor(this.colour);

        for(final Shape s : shapes) {
            g2d.draw(s);
        }
    }

    public void setArguments(int startX, int startY, int endX, int endY,
                             Color colour) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.colour = colour;
        addLine();
    }

    /** Add a new line to the panel. */
    public void addLine() {
        Line2D line = new Line2D.Double(startX, startY, endX, endY);
        shapes.add(line);
    }

    public Shape getLastShape(ArrayList<Shape> s) {
        try {
            return s.get(s.size() - 1);
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
        if (s == null) {
            System.err.println("Nothing to undo");
        } else {
            undoneShapes.add(s);
            shapes.remove(s);
        }
    }

    /** Redo the last removed shape. */
    public void redo() {
        Shape s = getLastShape(undoneShapes);
        if (s == null) {
            System.err.println("Nothing to redo");
        } else {
            undoneShapes.remove(s);
            shapes.add(s);
        }
    }
}
