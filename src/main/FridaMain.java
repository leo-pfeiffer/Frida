package main;

import controller.CalcController;
import controller.ICalcController;

import model.CalcModel;
import model.ICalcModel;

import view.CalcGUIView;
import view.FridaView;

/** Main file to run the drawing program "Frida", named after Frida Kahlo :).
 * @author 190026921 */
public class FridaMain {
    public static void main(String[] args) {
        // create Model
        // ICalcModel model = new CalcModel();

        // Create controller
        // ICalcController controller = new CalcController(model);

        // Create View (GUI)
        // new FridaView(model, controller);
        new FridaView();
    }
}
