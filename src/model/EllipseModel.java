package model;

import java.awt.Color;

/** Represents an ellipse that is created by a framing rectangle created from the
 * start to the end point (i.e. based on a single line).
 * @author 190026921 */
public class EllipseModel extends ShapeModel {

    /** X coordinate of top left corner. */
    private int x;

    /** Y coordinate of top left corner. */
    private int y;

    /** width of the ellipse. */
    private int w;

    /** height of the ellipse. */
    private int h;

    /** Fill colour of the shape. */
    private Color fillColour;

    /** Custom constructor. */
    public EllipseModel() { }

    /** Calculate positional variables. */
    private void calcPosition() {
        this.x  = Math.min(startX, endX);
        this.y = Math.min(startY, endY);
        this.w = Math.abs(startX - endX);

        // if lockAspect, h = w
        if (this.lockAspect) {
            this.h = this.w;
        } else {
            this.h = Math.abs(startY - endY);
        }
    }

    /** Get all positional variables.
     * @return array of all positional variables. */
    public int[] getPosition() {
        calcPosition();
        int[] position = {this.x, this.y, this.w, this.h};
        return position;
    }

    /** Set the fill colour of the model.
     * @param fillColour the new fill colour. */
    public void setFillColour(Color fillColour) {
        this.fillColour = fillColour;
    }

    /** Get the fill colour of the shape.
     * @return the fill colour of the model. */
    public Color getFillColour() {
        return this.fillColour;
    }
}
