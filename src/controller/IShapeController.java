package controller;

import java.awt.Point;

/** Defines behaviour for the Controllers of the different shapes. */
public interface IShapeController {

    void controlSetStartCoordinates(Point point);
    void controlSetEndCoordinates(Point point);
}
