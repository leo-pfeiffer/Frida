package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {

    /** X coordinate from where to draw the line. */
    private int startX;
    /** Y coordinate from where to draw the line. */
    private int startY;

    /** X coordinate to where to draw the line. */
    private int endX;
    /** Y coordinate to where to draw the line. */
    private int endY;

    public DrawPanel() { }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Line2D line = new Line2D.Double(startX, startY, endX, endY);
        g2d.draw(line);
        Ellipse2D curve = new Ellipse2D.Double(startX, startY, endX, endY);
        g2d.draw(curve);
    }

    public void setArguments(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }


}
