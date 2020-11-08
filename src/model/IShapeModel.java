package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/** This is an interface to be implemented by all shapes that can be drawn
 * by the user.
 * @author 190026921 */
public interface IShapeModel {

    /** Draw the shape. */
    void draw(Graphics g);

    void setColour(Color colour);

    /** Set the start coordinates of the shape.
     * @param point the points that define the start. */
    void setStartCoordinates(Point point);

    /** Set the end coordinates of the shape.
     * @param point the points that define the end. */
    void setEndCoordinates(Point point);
}
