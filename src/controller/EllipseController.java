package controller;

import model.EllipseModel;

import java.awt.Color;

public class EllipseController extends ShapeController {

    /** The model to be controlled. */
    private EllipseModel model;

    /**Custom constructor.
     * @param model the model to be controlled.*/
    public EllipseController(EllipseModel model) {
        super(model);
        this.model = model;
    }

    public void setFillColour(Color fillColour) {
        this.model.setFillColour(fillColour);
    }
}
