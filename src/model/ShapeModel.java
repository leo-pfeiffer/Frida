package model;

import java.awt.Graphics;
import java.awt.Color;

/** Abstract class that is inherited from by all shapes.
 * @author 190026921 */
public abstract class ShapeModel implements IShapeModel {

     private final Color colour;

    /** Custom constructor for the ShapeModel class.
     * @param colour Colour of the shape. */
    public ShapeModel(Color colour) {
        this.colour = colour;
    }

    /** Draw the shape to the Graphics object to be drawn on.
     * @param g The graphic to be drawn on. */
    public void draw(Graphics g) {
         g.setColor(colour);
    }

}
