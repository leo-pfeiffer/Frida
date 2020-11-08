package model;

import java.awt.Graphics;

/** This is an interface to be implemented by all shapes that can be drawn
 * by the user.
 * @author 190026921 */
public interface IShapeModel {

    /** Draw the shape. */
    public void draw(Graphics g);
}
