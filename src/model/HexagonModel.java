package model;

/** Represents a regular hexagon.
 * @author 190026921 */
public class HexagonModel extends ShapeModel2D {

    /** Custom constructor. */
    public HexagonModel() { }

    /** Calculate the corners of the hexagon. */
    private void calcPosition() {
        // Calculate circumradius of the hexagon using Pythagoras
        final int side1 = endX - startX;
        final int side2 = endY - startY;
        final double radius = 0.5  * Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2));

        // Calculate center point as 1/2 distance between start and end (must be integer
        // as it represents an actual point defined by pixels.
        final int midX = (int) (startX + 0.5 * side1);
        final int midY = (int) (startY + 0.5 * side2);

        // Create temporary arrays for the coordinates
        int[] xpoints = new int[6];
        int[] ypoints = new int[6];

        // from corner to corner, the angle from the mid point horizontal changes by 60 degrees.
        final double degMultiple = Math.toRadians(60);

        // calculate the corner coordinates by using the multiples of the 60 degree angle
        for (int i = 0; i < xpoints.length; i++) {
             xpoints[i] = (int) (radius * Math.cos((- degMultiple) * i) + midX);
             ypoints[i] = (int) (radius * Math.sin((- degMultiple) * i) + midY);
        }

        // set the coordinates
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
