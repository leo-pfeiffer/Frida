package model;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

/** Represents a single line that can be drawn by the user.
 * @author 190026921 */
public class LineModel extends ShapeModel implements IShapeModel {

    /** X coordinate from where to draw the line. */
    private final int FROM_X;
    /** Y coordinate from where to draw the line. */
    private final int FROM_Y;

    /** X coordinate to where to draw the line. */
    private final int TO_X;
    /** Y coordinate to where to draw the line. */
    private final int TO_Y;

    /** Custom constructor for a new Line.
     * @param colour The colour of the line.
     * @param from the point from where to draw the line.
     * @param to the point to where to draw the line. */
    public LineModel(Color colour, Point from, Point to) {
        super(colour);
        this.FROM_X = (int) from.getX();
        this.FROM_Y = (int) from.getY();
        this.TO_X = (int) to.getX();
        this.TO_Y = (int) to.getY();
    }

    @Override
    public void draw(Graphics g) {
        // set the colour
        super.draw(g);
        g.drawLine(this.FROM_X, this.FROM_Y, this.TO_X, this.TO_Y);

    }
}
