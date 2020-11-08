package model;

import java.awt.*;
import java.util.Observable;

/** Abstract class that is inherited from by all shapes.
 * @author 190026921 */
public abstract class ShapeModel extends Observable implements IShapeModel {

     private Color colour;

    /** Custom constructor for the ShapeModel class. */
    public ShapeModel() {}

    /** Draw the shape to the Graphics object to be drawn on.
     * @param g The graphic to be drawn on. */
    public void draw(Graphics g) {

        g.setColor(colour);
    }

    /** Set the colour of the shape.
     * @param colour Colour of the shape. */
    public void setColour(Color colour) {
        this.colour = colour;
    }
}
