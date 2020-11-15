package model;

/** Represents a rectangle.
 * @author 190026921 */
public class RectangleModel extends ShapeModel2D {

    /** Custom constructor. */
    public RectangleModel() { }

    /** Calculate the corners of the rectangle. */
    private void calcPosition() {
        //  Get the maxima and minima of the coordinates
        int xmin = Math.min(startX, endX);
        int xmax = Math.max(startX, endX);
        int ymin = Math.min(startY, endY);
        int ymax = Math.max(startY, endY);

        // Put them into arrays
        int[] xpoints = {xmin, xmax, xmax, xmin};
        int[] ypoints = {ymin, ymin, ymax, ymax};

        this.xpoints = xpoints;
        this.ypoints = ypoints;

        // Set the corners
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
