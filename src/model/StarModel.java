package model;

/** Represents a 5-point star.
 * @author 190026921 */
public class StarModel extends ShapeModel2D {

    /** Custom constructor. */
    public StarModel() { }

    /** Calculate the corners of the rectangle. */
    public void calcCorners() {
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

        int[] outerX = new int[outerDegrees.length];
        int[] outerY = new int[outerDegrees.length];

        int[] innerX = new int[innerDegrees.length];
        int[] innerY = new int[innerDegrees.length];

        int[] xpoints = new int[10];
        int[] ypoints = new int[10];


        for (int i = 0; i < outerDegrees.length; i++) {
            // innerX[i] = radius * (int) Math.cos(innerDegrees[i]);
            // innerY[i] = radius * (int) Math.sin(innerDegrees[i]);

            // outerX[i] = (int) (radius / 3 * Math.cos(outerDegrees[i]));
            // outerY[i] = (int) (radius / 3 * Math.sin(outerDegrees[i]));

            xpoints[2*i] = (int) (radius / 3 * Math.cos(outerDegrees[i]) + midX);
            xpoints[2*i+1] = (int) (radius * Math.cos(innerDegrees[i]) + midX);

            ypoints[2*i] = (int) (radius / 3 * Math.sin(outerDegrees[i]) + midY);
            ypoints[2*i+1] = (int) (radius * Math.sin(innerDegrees[i]) + midY);
        }

        // set the corners
        setCorners(xpoints, ypoints);
    }

    /**  {@inheritDoc} */
    @Override
    public int[][] getCorners() {
        calcCorners();
        return super.getCorners();
    }

}
