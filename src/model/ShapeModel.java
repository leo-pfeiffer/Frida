package model;

import java.awt.Color;
import java.util.Observable;

/** Abstract class that is inherited by all shapes.
 * @author 190026921 */
public abstract class ShapeModel extends Observable implements IShapeModel {

    /** Line colour of the shape. */
    private Color lineColour;

    /** X coordinate from where to draw the line. */
    protected int startX;
    /** Y coordinate from where to draw the line. */
    protected int startY;

    /** X coordinate to where to draw the line. */
    protected int endX;
    /** Y coordinate to where to draw the line. */
    protected int endY;

    protected int[] moveStart;
    protected int[] moveEnd;

    protected boolean lockAspect;

    /** Custom constructor. */
    public ShapeModel() {}

    /** {@inheritDoc} */
    public void update() {
        this.setChanged();
        this.notifyObservers();
    }

    /** {@inheritDoc} */
    public void setStartCoordinates(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    /** {@inheritDoc} */
    public void setEndCoordinates(int x, int y) {
        this.endX = x;
        this.endY = y;
        update();
    }

    /** {@inheritDoc} */
    public int[] getStartCoordinates() {
        return new int[] {this.startX, this.startY};
    }

    /** {@inheritDoc} */
    public int[] getEndCoordinates() {
        return new int[] {this.endX, this.endY};
    }

    /** {@inheritDoc} */
    public void setLineColour(Color lineColour) {
        this.lineColour = lineColour;
    }

    /** {@inheritDoc} */
    public Color getLineColour() {
        return this.lineColour;
    }

    public void setLockAspect(boolean lockAspect) {
        this.lockAspect = lockAspect;
    }

    @Override
    public void setMoveStart(int x, int y) {
        this.moveStart = new int[] {x, y};
    }

    @Override
    public void setMoveEnd(int x, int y) {
        this.moveEnd = new int[] {x, y};
    }

    @Override
    public void move() {
        int xDist = this.moveEnd[0] - this.moveStart[0];
        int yDist = this.moveEnd[1] - this.moveStart[1];
        this.startX += xDist;
        this.endX += xDist;
        this.startY += yDist;
        this.endY += yDist;
        update();
    }
}
