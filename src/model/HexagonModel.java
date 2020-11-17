package model;

/** Represents a regular hexagon.
 * @author 190026921 */
public class HexagonModel extends ShapeModel2D {

    /** Custom constructor. */
    public HexagonModel() { }

    /** Calculate the corners of the hexagon. */
    private void calcPosition() {
        // Calculate circumradius of the hexagon using Pythagoras
        final int a = endX - startX;
        final int b = endY - startY;
        final double radius = 0.5  * Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));

        // Calculate center point as 1/2 distance between start and end (must be integer
        // as it represents an actual point.
        final int midX = (int) (startX + 0.5 * a);
        final int midY = (int) (startY + 0.5 * b);

        int[] xpoints = new int[6];
        int[] ypoints = new int[6];

        final double degMultiple = Math.toRadians(60);

        for (int i = 0; i < xpoints.length; i++) {
             xpoints[i] = (int) (radius * Math.cos((- degMultiple) * i) + midX);
             ypoints[i] = (int) (radius * Math.sin((- degMultiple) * i) + midY);
        }

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
