package view;

import model.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
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
            // Get the shape and model that go together
            Shape shape = shapes.get(i);
            IShapeModel model = models.get(i);

            g2d.setStroke(new BasicStroke(model.getStrokeSize()));
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
        addShapeFromModel(model);
    }

    public void addShapeFromModel(IShapeModel model) {
        if (model instanceof LineModel) {
            addLine((LineModel) model);
        } else if(model instanceof EllipseModel) {
            addEllipse((EllipseModel) model);
        } else if (model instanceof ShapeModel2D){
            addPolygon((ShapeModel2D) model);
        }
    }

    public void updateLastShape() {
        IShapeModel m = getLastModel(models);
        Shape s = getLastShape(shapes);

        if (m instanceof LineModel) {
            int[] start = m.getStartCoordinates();
            int[] end = m.getEndCoordinates();
            ((Line2D) s).setLine(start[0], start[1], end[0], end[1]);
        }

        else if (m instanceof EllipseModel) {
            int[] pos = ((EllipseModel) m).getPosition();
            ((Ellipse2D) s).setFrame(pos[0], pos[1], pos[2], pos[3]);
        }

        else if (m instanceof ShapeModel2D){
            int[] xpoints = ((ShapeModel2D) m).getXpoints();
            int[] ypoints = ((ShapeModel2D) m).getYpoints();

            // For some reason Polygon is public so we can directly set
            // the new positional data
            ((Polygon) s).npoints = xpoints.length;
            ((Polygon) s).xpoints = xpoints;
            ((Polygon) s).ypoints = ypoints;
        }
    }

    /** Add a new line to the panel.
     * @param model The line model the line is based on.
     * */
    public void addLine(LineModel model) {
        int[] start = model.getStartCoordinates();
        int[] end = model.getEndCoordinates();

        Line2D line = new Line2D.Double(start[0], start[1], end[0], end[1]);
        shapes.add(line);
    }

    /** Add a new ellipse to the panel.
     * @param model The ellipse model the ellipse is based on.
     * */
    public void addEllipse(EllipseModel model) {

        // Get position of the ellipse
        int[] pos = model.getPosition();

        // Create the ellipse shape
        Ellipse2D ellipse = new Ellipse2D.Double(pos[0], pos[1], pos[2], pos[3]);
        shapes.add(ellipse);
    }

    /** Add a new polygon type shape to the panel.
     * @param model the model for any Polygon
     * */
    public void addPolygon(ShapeModel2D model) {

        int[] xpoints = model.getXpoints();
        int[] ypoints = model.getYpoints();

        // Create the polygon
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

    public IShapeModel getModelOnPoint (Point point) {
        final int size = shapes.size() - 1;
        for (int i = size; i >= 0; i--) {
            Shape shape = shapes.get(i);

            boolean isLine = false;

            if (shape instanceof Line2D) {

                // If click is within a 2 x 2 square of the line, accept.
                if (!shape.intersects((int) point.getX() - 1, (int) point.getY() - 1, 2, 2)) continue;

                isLine = true;
            }

            if (shape.contains(point) | isLine) {
                IShapeModel model = models.get(i);
                models.remove(i);
                shapes.remove(i);
                return model;
            }
        }
        return null;
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

    public void writeToFile(String filename) {
        WriteToFile.write(models, filename);
    }

    public void readFromFile(String filename) {
        try {
            System.out.println("Reading...");
            ArrayList<IShapeModel> newModels = ReadFromFile.read(filename);
            System.out.println("Done Reading...");

            // set the new models
            models = newModels;
            shapes.clear();
            for (IShapeModel m : models) {
                addShapeFromModel(m);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

}
