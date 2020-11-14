package model;

/** Represents a rectangle.
 * @author 190026921 */
public class RectangleModel extends ShapeModel2D {

    /** Custom constructor. */
    public RectangleModel() { }

    /** Setter method for the corners of the rectangle. */
    public void setCorners() {
        //  Get the maxima and minima of the coordinates
        int xmin = Math.min(startX, endX);
        int xmax = Math.max(startX, endX);
        int ymin = Math.min(startY, endY);
        int ymax = Math.max(startY, endY);

        // Put them into arrays
        int[] xpoints = {xmin, xmax, xmax, xmin};
        int[] ypoints = {ymin, ymin, ymax, ymax};

        // Initialise the corners array
        this.corners = new int[4][2];

        // Fill the corner coordinates into to the corners array
        for (int i = 0; i < xpoints.length; i++) {
            this.corners[i][0] = xpoints[i];
            this.corners[i][1] = ypoints[i];
        }
    }

    /**  {@inheritDoc} */
    @Override
    public int[][] getCorners() {
        setCorners();
        return super.getCorners();
    }

}
