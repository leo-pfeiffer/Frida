package view;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JColorChooser;

/** Extend JButton to create a button that opens
 * a colour picker in a new dialog window.
 * @author 190026921 */
public class ColourPicker extends JButton {

    /** Default colour of the colour picker. */
    private Color colour = Color.BLACK;

    /** Custom constructor.
     * @param text The button label. */
    // todo Make the current colour visible next to the button. Else just do this regular
    public ColourPicker(String text) {

        // set the button text
        this.setText(text);

        // On mouse click, open a JColorChooser Dialog
        addActionListener(e -> {
            Color pickedColour = JColorChooser.showDialog(null, "Pick a colour!", colour);
            setColour(pickedColour);
        });
    }

    /** Setter method to set the new colour.
     * @param colour The colour to be set. */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /** Getter method for the currently picked colour.
     * @return The currently active colour. */
    public Color getColour() {
        return this.colour;
    }
}
