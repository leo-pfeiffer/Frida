package controller;

import model.ShapeModel;

import java.awt.Color;

/** Shape Controller class used for models that are based on a single line (i.e. Line or Ellipse).
 * @author 190026921 */
public class ShapeController implements IShapeController {

    /** The model to be controlled. */
    private ShapeModel model;

    /** Custom constructor.
     * @param model the model to be controlled. */
    public ShapeController(ShapeModel model) {
        this.model = model;
    }

    /**  {@inheritDoc} */
    @Override
    public void setStartCoordinates(int x, int y) {
        model.setStartCoordinates(x, y);
    }

    /**  {@inheritDoc} */
    @Override
    public void setEndCoordinates(int x, int y) {
        model.setEndCoordinates(x, y);
    }

    /**  {@inheritDoc} */
    @Override
    public void setLineColour(Color lineColour) {
        model.setLineColour(lineColour);
    }

    /**  {@inheritDoc} */
    @Override
    public void setLockAspect(boolean lockAspect) {
        model.setLockAspect(lockAspect);
    }

    /**  {@inheritDoc} */
    @Override
    public void setMoveStart(int x, int y) {
        model.setMoveStart(x, y);
    }

    /**  {@inheritDoc} */
    @Override
    public void setMoveEnd(int x, int y) {
        model.setMoveEnd(x, y);
    }

    /**  {@inheritDoc} */
    @Override
    public void move() {
        model.move();
    }

    /**  {@inheritDoc} */
    @Override
    public void setStrokeSize(int size) { model.setStrokeSize(size);}
}
