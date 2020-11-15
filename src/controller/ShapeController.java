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

    @Override
    public void setLockAspect(boolean lockAspect) {
        model.setLockAspect(lockAspect);
    };

    @Override
    public void setMoveStart(int x, int y) {
        model.setMoveStart(x, y);
    }

    @Override
    public void setMoveEnd(int x, int y) {
        model.setMoveEnd(x, y);
    }
}
