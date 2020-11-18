package controller;

import java.awt.Color;

/** Defines behaviour for the Controllers of the different shapes.
 * @author 190026921 */
public interface IShapeController {

    /** Set the start coordinates of the shape.
     * @param x X coordinate
     * @param y Y coordinate */
    void setStartCoordinates(int x, int y);

    /** Set the end coordinates of the shape.
     * @param x X coordinate
     * @param y Y coordinate */
    void setEndCoordinates(int x, int y);

    /** Set the line colour of the shape.
     * @param lineColour colour to set the line to */
    void setLineColour(Color lineColour);

    /** Set whether the aspect ratio should be locked.
     * @param lockAspect if the aspect ratio is locked */
    void setLockAspect(boolean lockAspect);

    /** Set the starting point of a movement of a shape.
     * @param x X coordinate
     * @param y Y coordinate. */
    void setMoveStart(int x, int y);

    /** Set the end point of a movement of a shape.
     * @param x X coordinate
     * @param y Y coordinate. */
    void setMoveEnd(int x, int y);

    /** Move the shape as determined by the start point of the move
     * and the end point of the moved */
    void move();

    /** Set the stroke size of the line of the shape.
     * @param size Stroke size. */
    void setStrokeSize(int size);
}
