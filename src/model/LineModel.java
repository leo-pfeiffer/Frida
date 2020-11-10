package model;


/** Represents a single line that can be drawn by the user.
 * @author 190026921 */
public class LineModel extends ShapeModel implements IShapeModel {

    /** X coordinate from where to draw the line. */
    private int startX;
    /** Y coordinate from where to draw the line. */
    private int startY;

    /** X coordinate to where to draw the line. */
    private int endX;
    /** Y coordinate to where to draw the line. */
    private int endY;

    /** Custom constructor for a new Line. */
    public LineModel() { }

    private void update() {
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void setStartCoordinates(int x, int y) {
        this.startX = x;
        this.startY = y;
        System.out.println("Start point set");
    }

    @Override
    public void setEndCoordinates(int x, int y) {
        this.endX = x;
        this.endY = y;
        update();
        System.out.println("End point set");
    }

    @Override
    public int[] getStartCoordinates() {
        return new int[] {this.startX, this.startY};
    }

    @Override
    public int[] getEndCoordinates() {
        return new int[] {this.endX, this.endY};
    }
}
