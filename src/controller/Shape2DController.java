package controller;

import model.ShapeModel2D;

import java.awt.Color;

/** Class to define for two dimensional shapes.
 * @author 190026921 */
public class Shape2DController implements IShapeController{

    private ShapeModel2D model;

    public Shape2DController(ShapeModel2D model) {
        this.model = model;
    }

    @Override
    public void setStartCoordinates(int x, int y) {
        model.setStartCoordinates(x, y);
    }

    @Override
    public void setEndCoordinates(int x, int y) {
        model.setEndCoordinates(x, y);
    }

    @Override
    public void setLineColour(Color lineColour) {
        model.setLineColour(lineColour);
    }

    public void setFillColour(Color fillColour) {
        model.setFillColour(fillColour);
    }

}
