package view;

import model.EllipseModel;
import model.IShapeModel;
import model.LineModel;
import model.ReadFromFile;
import model.ShapeModel2D;
import model.WriteToFile;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.geom.Line2D;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** Panel the user can draw on.
 * @author 190026921 */
public class DrawPanel extends JPanel {

    /** Contains all shapes that are currently painted. */
    private ArrayList<Shape> shapes;
    /** Contains undone shapes. */
    private ArrayList<Shape> undoneShapes;

    /** The models corresponding to the shapes. */
    private ArrayList<IShapeModel> models;
    /** Contains undone models. */
    private ArrayList<IShapeModel> undoneModels;

    private FridaView frida;

    /** Custom constructor. */
    public DrawPanel(FridaView frida) {
        this.frida = frida;
        // set up the shapes and undone shapes
        shapes = new ArrayList<>();
        undoneShapes = new ArrayList<>();

        // set up the models and undone model
        models = new ArrayList<>();
        undoneModels = new ArrayList<>();
    }

    /** Overrides the paintComponent method of JComponent.
     * Paints all shapes to the panel. */
    @Override
    public void paintComponent(Graphics g) {

        // Create new g2d graphic
        Graphics2D g2d = (Graphics2D) g;

        // Iterate through all shapes
        for (int i=0; i < shapes.size(); i++) {

            // Get the shape and model that go together
            Shape shape = shapes.get(i);
            IShapeModel model = models.get(i);

            // Set the stroke, color and draw the shape
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

    /** Add a new model to the models array list. Also call the corresponding method to add the
     * shape to the shapes array list.
     * @param model The model to add. */
    public void addModel(IShapeModel model) {
        models.add(model);
        addShapeFromModel(model);
    }

    /** Add the shape from a model.
     * @param model the model the shape is based on. */
    public void addShapeFromModel(IShapeModel model) {

        // Handle line models
        if (model instanceof LineModel) {
            addLine((LineModel) model);
        }
        // Handle ellipse model
        else if(model instanceof EllipseModel) {
            addEllipse((EllipseModel) model);
        }
        // Handle any ShapeModel2D type model that's based on java.awt.Polygon
        else if (model instanceof ShapeModel2D){
            addPolygon((ShapeModel2D) model);
        }
    }

    /** Update the position of the most current shape. */
    public void updateLastShape() {

        // Get the last model and shape
        IShapeModel model = getLastModel(models);
        Shape shape = getLastShape(shapes);

        // Handle line model
        if (model instanceof LineModel) {
            int[] start = model.getStartCoordinates();
            int[] end = model.getEndCoordinates();
            ((Line2D) shape).setLine(start[0], start[1], end[0], end[1]);
        }

        // Handle ellipse
        else if (model instanceof EllipseModel) {
            int[] pos = ((EllipseModel) model).getPosition();
            ((Ellipse2D) shape).setFrame(pos[0], pos[1], pos[2], pos[3]);
        }

        // Handle models that are drawn by java.awt.Polygon
        else if (model instanceof ShapeModel2D){
            int[] xpoints = ((ShapeModel2D) model).getXpoints();
            int[] ypoints = ((ShapeModel2D) model).getYpoints();

            // For some reason Polygon is public so we can directly set the new positional data directly
            ((Polygon) shape).npoints = xpoints.length;
            ((Polygon) shape).xpoints = xpoints;
            ((Polygon) shape).ypoints = ypoints;
        }
    }

    /** Add a new line to the panel.
     * @param model The line model the line is based on. */
    public void addLine(LineModel model) {
        // get the start and end coordinates
        int[] start = model.getStartCoordinates();
        int[] end = model.getEndCoordinates();

        // Create the line and add it to the shapes array list
        Line2D line = new Line2D.Double(start[0], start[1], end[0], end[1]);
        shapes.add(line);
    }

    /** Add a new ellipse to the panel.
     * @param model The ellipse model the ellipse is based on.
     * */
    public void addEllipse(EllipseModel model) {

        // Get position of the ellipse
        int[] pos = model.getPosition();

        // Create the ellipse shape and add it to the shapes
        Ellipse2D ellipse = new Ellipse2D.Double(pos[0], pos[1], pos[2], pos[3]);
        shapes.add(ellipse);
    }

    /** Add a new polygon type shape to the panel.
     * @param model the model for any Polygon
     * */
    public void addPolygon(ShapeModel2D model) {

        // Get position of the model
        int[] xpoints = model.getXpoints();
        int[] ypoints = model.getYpoints();

        // Create the polygon and add to shapes array list
        Polygon rectangle = new Polygon(xpoints, ypoints, xpoints.length);
        shapes.add(rectangle);
    }

    /** Returns the last object of the ArrayList shapes passed to the method.
     * @param shape Shape ArrayList of which we want the last object.
     * @return Last shape if available else null*/
    public Shape getLastShape(ArrayList<Shape> shape) {
        try {
            return shape.get(shape.size() - 1);
        }
        // In case the shape array list is empty
        catch(IndexOutOfBoundsException ioobe) {
            return null;
        }
    }

    /** Returns the last object of the ArrayList models passed to the method.
     * @param model Model ArrayList of which we want the last object.
     * @return Last model if available else null*/
    public IShapeModel getLastModel(ArrayList<IShapeModel> model) {
        try {
            return model.get(model.size() - 1);
        }
        // In case the array is empty
        catch(IndexOutOfBoundsException ioobe) {
            return null;
        }
    }

    /** Get the most recently created model that is located on the point passed to the method.
     * @param point Point for which we want to check for models.
     * @return Most recently created model containing the point, or if none returns null. */
    public IShapeModel getModelOnPoint (Point point) {

        // Last index of array list
        final int size = shapes.size() - 1;

        // Search the array list backwards for a shape containing the point.
        for (int i = size; i >= 0; i--) {

            Shape shape = shapes.get(i);

            // Initialise boolean variable that tells us whether the current shape is a Line2D object
            boolean isLine = false;

            if (shape instanceof Line2D) {

                // If point is not within a 2 x 2 square of the line, jump to next iteration. Else, continue
                // handling the shape.
                if (!shape.intersects((int) point.getX() - 1, (int) point.getY() - 1, 2, 2)) continue;

                // Mark that the current shape is a line
                isLine = true;
            }

            // If shape contains point (or the line contains the point)
            if (shape.contains(point) | isLine) {
                // Get the model
                IShapeModel model = models.get(i);
                return model;
            }
        }
        return null;
    }

    /** Remove the model and shape of the current index from the array lists.
     * @param index The index of the objects in the array lists to be deleted. */
    public void removeModelAndShapeOnIndex(int index) {
        models.remove(index);
        shapes.remove(index);
    }

    /** Clear all array lists that contain graphics. */
    public void clearAll() {

        // Clear the shapes and models
        shapes.clear();
        models.clear();

        // Also clear undone shapes and models
        undoneShapes.clear();
        undoneModels.clear();
    }

    /** Remove last Shape. */
    public void undo() {
        Shape shape = getLastShape(shapes);
        IShapeModel model = getLastModel(models);
        if (shape == null & model == null) {
            // Idea for future improvement: Notify user that undo is impossible with a ping sound etc.
            System.out.println("Nothing to undo");
        } else {
            // handle shapes
            undoneShapes.add(shape);
            shapes.remove(shape);

            // handle models
            undoneModels.add(model);
            models.remove(model);
        }
    }

    /** Redo the last removed shape. */
    public void redo() {
        Shape shape = getLastShape(undoneShapes);
        IShapeModel model = getLastModel(undoneModels);
        if (shape == null & model == null) {
            // Idea for future improvement: Notify user that redo is impossible with a ping sound etc.
            System.out.println("Nothing to redo");
        } else {
            // handle shape
            undoneShapes.remove(shape);
            shapes.add(shape);

            // handle models
            undoneModels.remove(model);
            models.add(model);
        }
    }

    /** Write the current models to a file. Calls WriteToFile.write().
     * @param filename The desired name for a file. */
    public void writeToFile(String filename) {

        // Write the file
        try {
            WriteToFile.write(models, filename);
        }
        // In case of an error, show a warning to the user.
        catch (IOException ioe) {
            JOptionPane.showMessageDialog(this, "There was an error while saving your file.");
        }
    }

    /** Read models from a file. Calls ReadFromFile.read().
     * @param filename The name of the desired file. */
    public void readFromFile(String filename) {

        try {
            // Read the file and set them as the new models
            models = ReadFromFile.read(filename);

            // clear any currently existing shapes
            shapes.clear();
            undoneShapes.clear();
            undoneModels.clear();

            frida.clearControllers();

            // add the new shapes from the models read from the file.
            for (IShapeModel model : models) {
                frida.addModelFromFile(model);
                addShapeFromModel(model);
            }
        }
        // In case of an error display a message to the user.
        catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "There was an error while opening the file.");
        }
    }

    /** Get the models.
     * @return models currently on the drawpanel. */
    public ArrayList<IShapeModel> getModels() {
        return this.models;
    }

}
