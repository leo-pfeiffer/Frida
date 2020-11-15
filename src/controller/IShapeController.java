package controller;

import java.awt.Color;
/** Defines behaviour for the Controllers of the different shapes. */
public interface IShapeController {

    void setStartCoordinates(int x, int y);
    void setEndCoordinates(int x, int y);
    void setLineColour(Color lineColour);
    void setLockAspect(boolean lockAspect);
    void setMoveStart(int x, int y);
    void setMoveEnd(int x, int y);

}
