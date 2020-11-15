package model;

import java.awt.*;

/** Abstract class for all two dimensional models. The class extends its parent class
 * by adding a field that denotes the fill colour of the model and corresponding
 * getter and setter methods.
 * @author 190026921 */
public abstract class ShapeModel2D extends ShapeModel {

    /** Fill colour of the shape. */
    private Color fillColour;

    /** the x coordinates of the corners. */
    protected int[] xpoints;
    /** the x coordinates of the corners. */
    protected int[] ypoints;

    /** A two dimensional array containing the x,y coordinates of the corners of the 2D shape
     * or in the case of the Ellipsis, the start and end point of the diameter. */
    protected int[][] corners;

    /** Custom constructor. */
    public ShapeModel2D() {}

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

    /** Getter method for the corners.
     * @return the corners as a 2d integer array. */
    public int[][] getCorners() {
        return this.corners;
    }

    /** Set the corners.*/
    public void setCorners() {
        
        this.corners = new int[this.xpoints.length][2];

        // Fill the corner coordinates into to the corners array
        for (int i = 0; i < this.xpoints.length; i++) {
            this.corners[i][0] = this.xpoints[i];
            this.corners[i][1] = this.ypoints[i];
        }
    }

    public int[] getXpoints() {
        return xpoints;
    }

    public int[] getYpoints() {
        return ypoints;
    }
}
