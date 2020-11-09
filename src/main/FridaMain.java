package main;

import controller.IShapeController;
import controller.LineController;

import model.IShapeModel;
import model.LineModel;
import view.FridaView;

import javax.sound.sampled.Line;

/** Main file to run the drawing program "Frida", named after Frida Kahlo :).
 * @author 190026921 */
public class FridaMain {
    public static void main(String[] args) {
        // Create View (GUI)
        new FridaView();
    }
}
