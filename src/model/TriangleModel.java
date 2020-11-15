package model;

/** Represents a triangle.
 * @author 190026921 */
public class TriangleModel extends ShapeModel2D {

    /** Custom constructor. */
    public TriangleModel() { }

    /** Setter method for the corners of the triangle. */
    private void calcPosition() {

        // Calculate corners
        int[] xpoints = {startX, (startX + startX) - endX, endX};
        int[] ypoints = {startY, endY, endY};

        this.xpoints = xpoints;
        this.ypoints = ypoints;

        // set the corners
        setCorners();
    }

    /**  {@inheritDoc} */
    @Override
    public int[][] getCorners() {
        calcPosition();
        return super.getCorners();
    }

    /**  {@inheritDoc} */
    @Override
    public int[] getXpoints() {
        calcPosition();
        return xpoints;
    }

    /**  {@inheritDoc} */
    @Override
    public int[] getYpoints() {
        calcPosition();
        return ypoints;
    }

}
