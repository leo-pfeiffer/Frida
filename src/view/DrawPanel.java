package view;

import model.IShapeModel;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.Color;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {

    /** Contains all shapes that are currently painted. */
    private ArrayList<Shape> shapes;
    /** Contains undone shapes. */
    private ArrayList<Shape> undoneShapes;

    /** Contains all colours corresponding to the shapes. */
    private ArrayList<Color> colours;
    /** Contains colours of undone shapes. */
    private ArrayList<Color> undoneColours;

    /** The models corresponding to the shapes. */
    private ArrayList<IShapeModel> models;
    /** Contains undone models. */
    private ArrayList<IShapeModel> undoneModels;


    public DrawPanel() {
        shapes = new ArrayList<>();
        undoneShapes = new ArrayList<>();

        colours = new ArrayList<>();
        undoneColours = new ArrayList<>();

        models = new ArrayList<>();
        undoneModels = new ArrayList<>();
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

    public void addModel(IShapeModel model) {
        models.add(model);
        int x1 = model.getStartCoordinates()[0];
        int y1 = model.getStartCoordinates()[1];
        int x2 = model.getEndCoordinates()[0];
        int y2 = model.getEndCoordinates()[1];
        addLine(x1, y1, x2, y2);
        addColour(model.getLineColour());
    }

    /** Add a new line to the panel.
     * @param x1 X position of start coordinate
     * @param y1 Y position of start coordinate
     * @param x2 X position of end coordinate
     * @param y2 Y position of end coordinate
     * */
    public void addLine(int x1, int y1, int x2, int y2) {
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
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

    public IShapeModel getLastModel(ArrayList<IShapeModel> m) {
        try {
            return m.get(m.size() - 1);
        } catch(IndexOutOfBoundsException ioobe) {
            return null;
        }
    }

    /** Clear all array lists that contain graphics. */
    public void clearAll() {
        // todo Add them to the undone models
        shapes.clear();
        colours.clear();
        models.clear();

    }

    /** Remove last Shape. */
    public void undo() {
        Shape s = getLastShape(shapes);
        Color c = getLastColour(colours);
        IShapeModel m = getLastModel(models);
        if (s == null & c == null) {
            System.err.println("Nothing to undo");
        } else {
            // handle shapes
            undoneShapes.add(s);
            shapes.remove(s);

            // handle colours
            undoneColours.add(c);
            colours.remove(c);

            // handle models
            undoneModels.add(m);
            models.remove(m);

        }
    }

    /** Redo the last removed shape. */
    public void redo() {
        Shape s = getLastShape(undoneShapes);
        Color c = getLastColour(undoneColours);
        IShapeModel m = getLastModel(undoneModels);
        if (s == null & c == null) {
            System.err.println("Nothing to redo");
        } else {
            // handle shape
            undoneShapes.remove(s);
            shapes.add(s);

            // handle colours
            undoneColours.remove(c);
            colours.add(c);

            // handle models
            undoneModels.remove(m);
            models.add(m);
        }
    }
}
