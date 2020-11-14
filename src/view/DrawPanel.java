package view;

import model.IShapeModel;
import model.LineModel;
import model.RectangleModel;
import model.ShapeModel2D;

import java.awt.*;
import java.util.ArrayList;

import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {

    /** Contains all shapes that are currently painted. */
    private ArrayList<Shape> shapes;
    /** Contains undone shapes. */
    private ArrayList<Shape> undoneShapes;

    /** The models corresponding to the shapes. */
    private ArrayList<IShapeModel> models;
    /** Contains undone models. */
    private ArrayList<IShapeModel> undoneModels;


    public DrawPanel() {
        shapes = new ArrayList<>();
        undoneShapes = new ArrayList<>();

        models = new ArrayList<>();
        undoneModels = new ArrayList<>();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int i=0; i < shapes.size(); i++) {
            // Get the shape and colour that go together
            Shape shape = shapes.get(i);
            IShapeModel model = models.get(i);

            // todo let the user set the stroke
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(model.getLineColour());
            g2d.draw(shape);

            // If 2D model, add fill colour
            if (model instanceof ShapeModel2D) {
                g2d.setPaint(((ShapeModel2D) model).getFillColour());
                g2d.fill(shape);
            }
        }
    }

    public void addModel(IShapeModel model) {
        models.add(model);
        if (model instanceof LineModel) {
            addLine((LineModel) model);
        } else if (model instanceof RectangleModel){
            addRectangle((ShapeModel2D) model);
        }
    }

    /** Add a new line to the panel.
     * @param model The line model the line is based on.
     * */
    public void addLine(LineModel model) {
        int x1 = model.getStartCoordinates()[0];
        int y1 = model.getStartCoordinates()[1];
        int x2 = model.getEndCoordinates()[0];
        int y2 = model.getEndCoordinates()[1];
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
        shapes.add(line);
    }

    /** Add a new rectangle to the panel.
     * @param model the model for the rectangle
     * */
    public void addRectangle(ShapeModel2D model) {
        // Rectangle2D rectangle = new Rectangle2D.Double(x1, y1, x2, y2);
        int[][] corners = model.getCorners();
        int[] xpoints = new int[4];
        int[] ypoints = new int[4];

        for (int i = 0; i < 4; i++) {
            xpoints[i] = corners[i][0];
            ypoints[i] = corners[i][1];
        }

        Polygon rectangle = new Polygon(xpoints, ypoints, xpoints.length);
        shapes.add(rectangle);
    }

    public Shape getLastShape(ArrayList<Shape> s) {
        try {
            return s.get(s.size() - 1);
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
        // Add them to the undone models and shapes
        for (int i = 0; i < shapes.size(); i++) {
            undoneModels.add(models.get(i));
            undoneShapes.add(shapes.get(i));
        }

        // Clear the shapes and models
        shapes.clear();
        models.clear();

    }

    /** Remove last Shape. */
    public void undo() {
        Shape s = getLastShape(shapes);
        IShapeModel m = getLastModel(models);
        if (s == null & m == null) {
            System.err.println("Nothing to undo");
        } else {
            // handle shapes
            undoneShapes.add(s);
            shapes.remove(s);

            // handle models
            undoneModels.add(m);
            models.remove(m);

        }
    }

    /** Redo the last removed shape. */
    public void redo() {
        Shape s = getLastShape(undoneShapes);
        IShapeModel m = getLastModel(undoneModels);
        if (s == null & m == null) {
            System.err.println("Nothing to redo");
        } else {
            // handle shape
            undoneShapes.remove(s);
            shapes.add(s);

            // handle models
            undoneModels.remove(m);
            models.add(m);
        }
    }
}
