package controller;

import model.ShapeModel;

import java.awt.Color;

public class ShapeController implements IShapeController {

    private ShapeModel model;

    public ShapeController(ShapeModel model) {
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
}
