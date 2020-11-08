package model;

import java.awt.Graphics;
import java.awt.Point;

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
    public void draw(Graphics g) {
        // set the colour
        super.draw(g);
        g.drawLine(this.startX, this.startY, this.endX, this.endY);

    }

    @Override
    public void setStartCoordinates(Point point) {
        this.startX = (int) point.getX();
        this.startY = (int) point.getY();
        update();
        System.out.println("Start point set");
    }

    @Override
    public void setEndCoordinates(Point point) {
        this.endX = (int) point.getX();
        this.endY = (int) point.getY();
        update();
        System.out.println("End point set");
    }
}
