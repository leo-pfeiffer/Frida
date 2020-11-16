package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/** Extend JButton to create a button that opens
 * a colour picker in a new dialog window.
 * @author 190026921 */
public class ColourPicker extends JButton {

    /** Default colour of the colour picker. */
    private Color colour;

    /** Custom constructor.
     * @param text The button label.
     * @param colour The default colour.*/
    public ColourPicker(String text, Color colour) {

        // set the button text
        this.setText(text);

        setupIcon(colour);

        // set default colour
        this.colour = colour;

        // On mouse click, open a JColorChooser Dialog
        addActionListener(e -> {
            Color pickedColour = JColorChooser.showDialog(null, text, colour);
            setColour(pickedColour);
            setupIcon(pickedColour);
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

    public void setupIcon(Color colour) {
        // add icon
        BufferedImage iconImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = iconImage.createGraphics();

        graphics.setPaint(colour);
        graphics.fillRect(0, 0, iconImage.getWidth(), iconImage.getHeight());

        this.setIcon(new ImageIcon(iconImage));
    }
}
