package model;

import java.awt.Color;

/** This is an interface to be implemented by all shapes that can be drawn
 * by the user.
 * @author 190026921 */
public interface IShapeModel {

    /** Method to call setChanged and notifyObserver. */
    void update();

    /** set the colour of the object.
     * @param colour The new colour. */
    void setColour(Color colour);

    /** get the colour of the object.
     * @return colour The new colour. */
    Color getColour();

    /** Set the start coordinates of the shape.
     * @param x The x coordinate of the start point.
     * @param y The y coordinate of the start point. */
    void setStartCoordinates(int x, int y);

    /** Set the end coordinates of the shape.
     * @param x The x coordinate of the end point.
     * @param y The y coordinate of the end point. */
    void setEndCoordinates(int x, int y);

    /** Get the start coordinates of the shape.
     * @return An array of integers {x, y}, the coordinates of the start point. */
    int[] getStartCoordinates();

    /** Get the end coordinates of the shape.
     * @return An array of integers {x, y}, the coordinates of the end point. */
    int[] getEndCoordinates();
}
