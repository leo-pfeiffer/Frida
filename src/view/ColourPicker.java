package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

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

        // Set up the icon, i.e. the current colour that's displayed next to the button
        setupIcon(colour);

        // set default colour
        this.colour = colour;

        // On mouse click, open a JColorChooser Dialog
        addActionListener(e -> {
            // Show the color chooser dialog
            Color pickedColour = JColorChooser.showDialog(null, text, colour);

            // set the picked colour as the colour
            setColour(pickedColour);

            // refresh the icon to show the current colour
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
        // Create an empty image that is put on the icon
        BufferedImage iconImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

        // Create a graphic from the image
        Graphics2D graphics = iconImage.createGraphics();

        // Set the colour and shape of the icon
        graphics.setPaint(colour);
        graphics.fillRect(0, 0, iconImage.getWidth(), iconImage.getHeight());

        // set the icon to the button
        this.setIcon(new ImageIcon(iconImage));
    }
}
