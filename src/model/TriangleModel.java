package model;

/** Represents a triangle.
 * @author 190026921 */
public class TriangleModel extends ShapeModel2D {

    /** Custom constructor. */
    public TriangleModel() { }

    /** Setter method for the corners of the rectangle. */
    public void calcCorners() {

        // Calculate corners
        int[] xpoints = {startX, (startX + startX) - endX, endX};
        int[] ypoints = {startY, endY, endY};

        // set the corners
        setCorners(xpoints, ypoints);
    }

    /**  {@inheritDoc} */
    @Override
    public int[][] getCorners() {
        calcCorners();
        return super.getCorners();
    }

}
