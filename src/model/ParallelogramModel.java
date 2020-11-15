package model;

/** Represents a parallelogram.
 * @author 190026921 */
public class ParallelogramModel extends ShapeModel2D {

    /** Custom constructor. */
    public ParallelogramModel() { }

    /** Calculate the corners of the rectangle. */
    public void calcCorners() {

        final float skew = 0.67f;

        //  Get the maxima and minima of the coordinates
        int xmin = Math.min(startX, endX);
        int xmax = Math.max(startX, endX);
        int ymin = Math.min(startY, endY);
        int ymax = Math.max(startY, endY);

        int width = xmax - xmin;

        // Y are always the same
        int[] ypoints = {ymin, ymin, ymax, ymax};

        // Top left to bottom right
        if ((startX < endX & startY < endY)
                | (startX > endX & startY > endY)) {
            // Put them into arrays
            int[] xpoints = {xmin, xmin + (int) (skew * width),
                    xmax, xmin + (int) ((1 - skew) * width)};

            // Set the corners
            setCorners(xpoints, ypoints);
        }
        // Top right to bottom left
        else if ((startX > endX & startY < endY)
                | (startX < endX & startY > endY)) {
            int[] xpoints = {xmin + (int) ((1 - skew) * width),
                    xmax, xmin + (int) (skew * width), xmin};

            // Set the corners
            setCorners(xpoints, ypoints);
        }
    }

    /**  {@inheritDoc} */
    @Override
    public int[][] getCorners() {
        calcCorners();
        return super.getCorners();
    }

}
