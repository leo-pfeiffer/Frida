package controller;

import model.LineModel;

import java.awt.Color;

public class LineController implements IShapeController {

    private LineModel model;

    public LineController(LineModel model) {
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
