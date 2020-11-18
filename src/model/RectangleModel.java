package model;

/** Represents a rectangle.
 * @author 190026921 */
public class RectangleModel extends ShapeModel2D {

    /** Custom constructor. */
    public RectangleModel() { }

    /** Calculate the x and y coordinates of the corners of the parallelogram. */
    private void calcPosition() {
        //  Get the maxima and minima of the coordinates
        int xmin = Math.min(startX, endX);
        int xmax = Math.max(startX, endX);
        int ymin = Math.min(startY, endY);
        int ymax = Math.max(startY, endY);

        // Declare arrays for coordinates
        int[] xpoints;
        int[] ypoints;

        // If aspect ratio is not locked
        if (!this.lockAspect) {
            xpoints = new int[]{xmin, xmax, xmax, xmin};
            ypoints = new int[]{ymin, ymin, ymax, ymax};
        }

        // if aspect ratio is locked, h = w (square)
        else {
            int h = Math.abs(endY - startY);
            xpoints = new int[]{xmin, xmin + h, xmin + h, xmin};
            ypoints = new int[]{ymin, ymin, ymin + h, ymin + h};
        }

        // Save the the x and y coordinates of the model
        this.xpoints = xpoints;
        this.ypoints = ypoints;

        // Set the corners
        setCorners();
    }

    /**  {@inheritDoc} */
    @Override
    public int[][] getCorners() {
        // calculate the x and y coordinates of the corners.
        calcPosition();
        return super.getCorners();
    }

    /**  {@inheritDoc} */
    @Override
    public int[] getXpoints() {
        // calculate the x and y coordinates of the corners.
        calcPosition();
        return super.getXpoints();
    }

    /**  {@inheritDoc} */
    @Override
    public int[] getYpoints() {
        // calculate the x and y coordinates of the corners.
        calcPosition();
        return super.getYpoints();
    }
}
