package model;

/** Represents a 5-point star.
 * @author 190026921 */
public class StarModel extends ShapeModel2D {

    /** Custom constructor. */
    public StarModel() { }

    /** Calculate the corners of the star. */
    private void calcPosition() {
        // Calculate radius using Pythagoras
        final int a = endX - startX;
        final int b = endY - startY;
        final int radius = (int) (0.5  * Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));

        // Calculate center point as 1/2 distance between start and end
        final int midX = (int) (startX + 0.5 * a);
        final int midY = (int) (startY + 0.5 * b);

        // Calculate x coordinates of the points on the inner and outer circle
        final double[] outerDegrees = {Math.toRadians(18), Math.toRadians(90),
                Math.toRadians(162), Math.toRadians(234), Math.toRadians(306)};
        final double[] innerDegrees = {Math.toRadians(54), Math.toRadians(126),
                Math.toRadians(198), Math.toRadians(270), Math.toRadians(342)};

        int[] xpoints = new int[10];
        int[] ypoints = new int[10];


        for (int i = 0; i < outerDegrees.length; i++) {

            xpoints[2*i] = (int) (radius / 3 * Math.cos(outerDegrees[i]) + midX);
            xpoints[2*i+1] = (int) (radius * Math.cos(innerDegrees[i]) + midX);

            ypoints[2*i] = (int) (radius / 3 * Math.sin(outerDegrees[i]) + midY);
            ypoints[2*i+1] = (int) (radius * Math.sin(innerDegrees[i]) + midY);
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
