package model;

/** Represents a single line. A line is the simplest of
 * all the implemented shapes so it extends ShapeModel without adding any additional
 * methods or attributes.
 * @author 190026921 */
public class LineModel extends ShapeModel {
    /** Custom constructor. */
    public LineModel() { }

    @Override
    public void move() {
        int xDist = this.moveEnd[0] - this.moveStart[0];
        int yDist = this.moveEnd[1] - this.moveStart[1];
        this.startX += xDist;
        this.endX += xDist;
        this.startY += yDist;
        this.endY += yDist;
    }
}
