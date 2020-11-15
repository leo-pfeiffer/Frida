package model;

/** Represents an ellipse that is created by a framing rectangle created from the
 * start to the end point.
 * @author 190026921 */
public class EllipseModel extends ShapeModel {

    // X coordinate of top left corner.
    private int x;
    // Y coordinate of top left corner.
    private int y;
    // width of the ellipse.
    private int w;
    // height of the ellipse.
    private int h;

    /** Custom constructor. */
    public EllipseModel() { }

    /** Calculate positional variables. */
    private void calcPosition() {
        this.x  = Math.min(startX, endX);
        this.y = Math.min(startY, endY);
        this.w = Math.abs(startX - endX);
        this.h = Math.abs(startY - endY);
    }

    /** Get all positional variables.
     * @return array of all positional variables. */
    public int[] getPosition() {
        calcPosition();
        int[] position = {this.x, this.y, this.w, this.h};
        return position;
    }

}
