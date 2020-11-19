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

    /** X and Y coordinate of the start of a move. */
    protected int[] moveStart;
    /** X and Y coordinate of the end of a move. */
    protected int[] moveEnd;

    /** True if the aspect ratio is locked, else false. */
    protected boolean lockAspect;

    /** Stroke size of the line of the model. */
    private int strokeSize;

    /** Custom constructor. */
    public ShapeModel() {}

    /** {@inheritDoc} */
    @Override
    public void update() {
        this.setChanged();
        this.notifyObservers();
    }

    /** {@inheritDoc}
     * @throws IllegalArgumentException If negative coordinates are passed. */
    @Override
    public void setStartCoordinates(int x, int y) throws IllegalArgumentException{
        if ((x < 0) | (y < 0)) {
            throw new IllegalArgumentException();
        }
        this.startX = x;
        this.startY = y;
    }

    /** {@inheritDoc}
     * @throws IllegalArgumentException If negative coordinates are passed. */
    @Override
    public void setEndCoordinates(int x, int y) throws IllegalArgumentException {
        if ((x < 0) | (y < 0)) {
            throw new IllegalArgumentException();
        }
        this.endX = x;
        this.endY = y;
        update();
    }

    /** {@inheritDoc} */
    @Override
    public int[] getStartCoordinates() {
        return new int[] {this.startX, this.startY};
    }

    /** {@inheritDoc} */
    @Override
    public int[] getEndCoordinates() {
        return new int[] {this.endX, this.endY};
    }

    /** {@inheritDoc} */
    @Override
    public void setLineColour(Color lineColour) {
        this.lineColour = lineColour;
    }

    /** {@inheritDoc} */
    @Override
    public Color getLineColour() {
        return this.lineColour;
    }

    /** {@inheritDoc} */
    @Override
    public void setLockAspect(boolean lockAspect) {
        this.lockAspect = lockAspect;
    }

    /** {@inheritDoc} */
    @Override
    public boolean getLockAspect() {
        return this.lockAspect;
    }

    /** {@inheritDoc}
     * @throws IllegalArgumentException if negative coordinates are passed. */
    @Override
    public void setMoveStart(int x, int y) throws IllegalArgumentException{
        if ((x < 0) | (y < 0)) {
            throw new IllegalArgumentException();
        }
        this.moveStart = new int[] {x, y};
    }

    /** {@inheritDoc}
     * @throws IllegalArgumentException if negative coordinates are passed. */
    @Override
    public void setMoveEnd(int x, int y) {
        if ((x < 0) | (y < 0)) {
            throw new IllegalArgumentException();
        }
        this.moveEnd = new int[] {x, y};
    }

    /** {@inheritDoc} */
    @Override
    public int[] getMoveStart() {
        return this.moveStart;
    }

    /** {@inheritDoc} */
    @Override
    public int[] getMoveEnd() {
        return this.moveEnd;
    }

    /** {@inheritDoc}
     * @throws IllegalArgumentException if a negative integer is passed. */
    @Override
    public void setStrokeSize(int size) throws IllegalArgumentException{
        if (size < 1) {
            throw new IllegalArgumentException();
        }
        this.strokeSize = size;
    }

    /** {@inheritDoc} */
    @Override
    public int getStrokeSize() { return this.strokeSize; }

    /** {@inheritDoc} */
    @Override
    public void move() {
        // Calculate x and y movement
        int xDist = this.moveEnd[0] - this.moveStart[0];
        int yDist = this.moveEnd[1] - this.moveStart[1];

        // Update start and end of the shape.
        this.startX += xDist;
        this.endX += xDist;
        this.startY += yDist;
        this.endY += yDist;
        update();
    }
}
