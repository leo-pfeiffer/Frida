package model;

import java.awt.Color;
import java.util.Observable;

/** Abstract class that is inherited by all shapes.
 * @author 190026921 */
public abstract class ShapeModel extends Observable implements IShapeModel {

    /** Colour of the shape. */
    private Color colour;

    /** X coordinate from where to draw the line. */
    private int startX;
    /** Y coordinate from where to draw the line. */
    private int startY;

    /** X coordinate to where to draw the line. */
    private int endX;
    /** Y coordinate to where to draw the line. */
    private int endY;

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
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /** {@inheritDoc} */
    public Color getColour() {
        return this.colour;
    }
}
