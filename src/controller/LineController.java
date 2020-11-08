package controller;

import model.LineModel;

import java.awt.Point;

public class LineController implements IShapeController {

    private LineModel model;

    public LineController(LineModel model) {
        this.model = model;
    }

    @Override
    public void controlSetStartCoordinates(Point point) {
        model.setStartCoordinates(point);
    }

    @Override
    public void controlSetEndCoordinates(Point point) {
        model.setEndCoordinates(point);
    }
}
