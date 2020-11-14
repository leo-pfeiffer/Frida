package model;

/** Represents a rectangle.
 * @author 190026921 */
public class RectangleModel extends ShapeModel2D {

    /** Custom constructor. */
    public RectangleModel() { }

    /** Calculate the corners of the rectangle. */
    public void calcCorners() {
        //  Get the maxima and minima of the coordinates
        int xmin = Math.min(startX, endX);
        int xmax = Math.max(startX, endX);
        int ymin = Math.min(startY, endY);
        int ymax = Math.max(startY, endY);

        // Put them into arrays
        int[] xpoints = {xmin, xmax, xmax, xmin};
        int[] ypoints = {ymin, ymin, ymax, ymax};

        // Set the corners
        setCorners(xpoints, ypoints);
    }

    /**  {@inheritDoc} */
    @Override
    public int[][] getCorners() {
        calcCorners();
        return super.getCorners();
    }

}
