package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * @author 190026921 */
public class StrokeStyler extends JButton {

    /** Default colour of the colour picker. */
    private String stroke;

    private final int THICK = 6;
    private final int MEDIUM = 3;
    private final int THIN = 1;

    /** Custom constructor.
     * @param text The button label.*/
    public StrokeStyler(String text) {

        // set the button text
        this.setText(text);
        this.setMaximumSize(new Dimension(150, 1000));

        String[] strokes = new String[] {"thin line", "medium line", "thick line"};

        JComboBox<String> strokeList = new JComboBox<>(strokes);

        strokeList.addActionListener(event -> {
            JComboBox<String> combo = (JComboBox<String>) event.getSource();
            this.stroke = (String) combo.getSelectedItem();
        });

        // add to button
        this.add(strokeList);

        // get the selected stroke
        this.stroke = (String) strokeList.getSelectedItem();
    }

    public int getStrokeSize() {
        if (this.stroke.equals("thick line")) {
            return THICK;
        } else if (this.stroke.equals("medium line")) {
            return MEDIUM;
        } else {
            return THIN;
        }
    }
}
