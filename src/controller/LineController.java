package controller;

import model.LineModel;

public class LineController implements IShapeController {

    private LineModel model;

    public LineController(LineModel model) {
        this.model = model;
    }

    @Override
    public void controlSetStartCoordinates(int x, int y) {
        model.setStartCoordinates(x, y);
    }

    @Override
    public void controlSetEndCoordinates(int x, int y) {
        model.setEndCoordinates(x, y);
    }
}
