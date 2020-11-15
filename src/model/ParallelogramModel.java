package model;

/** Represents a parallelogram.
 * @author 190026921 */
public class ParallelogramModel extends ShapeModel2D {

    /** Custom constructor. */
    public ParallelogramModel() { }

    /** Calculate the corners of the parallelogram. */
    private void calcPosition() {

        final float skew = 0.67f;

        //  Get the maxima and minima of the coordinates
        int xmin = Math.min(startX, endX);
        int xmax = Math.max(startX, endX);
        int ymin = Math.min(startY, endY);
        int ymax = Math.max(startY, endY);

        int width = xmax - xmin;

        // Y are always the same
        int[] ypoints = {ymin, ymin, ymax, ymax};
        this.ypoints = ypoints;

        // Top left to bottom right
        if ((startX <= endX & startY <= endY)
                | (startX >= endX & startY >= endY)) {
            // Put them into arrays
            int[] xpoints = {xmin, xmin + (int) (skew * width),
                    xmax, xmin + (int) ((1 - skew) * width)};
            this.xpoints = xpoints;

        }

        // Top right to bottom left
        else {
            int[] xpoints = {xmin + (int) ((1 - skew) * width),
                    xmax, xmin + (int) (skew * width), xmin};
            this.xpoints = xpoints;
        }

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
