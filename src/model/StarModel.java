package model;

/** Represents a 5-point star.
 * @author 190026921 */
public class StarModel extends ShapeModel2D {

    /** Custom constructor. */
    public StarModel() { }

    /** Calculate the corners of the rectangle. */
    public void calcCorners() {

    }

    /**  {@inheritDoc} */
    @Override
    public int[][] getCorners() {
        calcCorners();
        return super.getCorners();
    }

}
