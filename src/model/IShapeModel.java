package model;

import java.awt.Color;

/** This is an interface to be implemented by all shapes that can be drawn
 * by the user.
 * @author 190026921 */
public interface IShapeModel {

    /** set the colour of the object.
     * @param colour The new colour. */
    void setColour(Color colour);

    /** Set the start coordinates of the shape. */
    void setStartCoordinates(int x, int y);

    /** Set the end coordinates of the shape.*/
    void setEndCoordinates(int x, int y);

    /** Get the start coordinates of the shape. */
    int[] getStartCoordinates();

    /** Get the end coordinates of the shape.*/
    int[] getEndCoordinates();
}
