package model;

import java.awt.Color;

/** Abstract class for all two dimensional models. The class extends its parent class
 * by adding a field that denotes the fill colour of the model and corresponding
 * getter and setter methods.
 * @author 190026921 */
public abstract class ShapeModel2D extends ShapeModel {

    /** Fill colour of the shape. */
    private Color fillColour;

    /** Custom constructor. */
    public ShapeModel2D() {}

    /** Set the fill colour of the model.
     * @param fillColour the new fill colour. */
    public void setFillColour(Color fillColour) {
        this.fillColour = fillColour;
    }

    /** Get the fill colour of the shape.
     * @return the fill colour of the model. */
    public Color getFillColour() {
        return this.fillColour;
    }

}
