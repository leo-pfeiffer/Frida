package model;

import java.awt.Color;
import java.io.Serializable;

/** This is an interface to be implemented by all shapes that can be drawn
 * by the user.
 * @author 190026921 */
public interface IShapeModel extends Serializable {

    /** Method to call setChanged and notifyObserver. */
    void update();

    /** set the line colour of the object.
     * @param lineColour The new line colour. */
    void setLineColour(Color lineColour);

    /** get the line colour of the object.
     * @return The line colour. */
    Color getLineColour();

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

    /** Set the starting point of a movement of a shape.
     * @param x X coordinate
     * @param y Y coordinate. */
    void setMoveStart(int x, int y);

    /** Get the starting point of a movement of a shape.
     * @return X and Y coordinates. */
    int[] getMoveStart();

    /** Set the end point of a movement of a shape.
     * @param x X coordinate
     * @param y Y coordinate. */
    void setMoveEnd(int x, int y);

    /** Get the end point of a movement of a shape.
     * @return X and Y coordinates. */
    int[] getMoveEnd();

    /** Move the shape as determined by the start point of the move
     * and the end point of the moved */
    void move();

    /** Set the stroke size of the line of the shape.
     * @param size Stroke size. */
    void setStrokeSize(int size);

    /** Get the stroke size of the line of the shape.
     * @return Stroke size. */
    int getStrokeSize();

    /** Set the lock aspect ratio to true or false.
     * @param lockAspect true if the ratio is locked, else false. */
    void setLockAspect(boolean lockAspect);

    /** Get the lock aspect ratio to true or false.
     * @return true if the ratio is locked, else false. */
    boolean getLockAspect();
}
