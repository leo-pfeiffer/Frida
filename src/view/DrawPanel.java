package view;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.sound.sampled.Line;
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

    // private ArrayList<Graphics2D> graphs = new ArrayList<Graphics2D>();
    private ArrayList<Line2D> lines = new ArrayList<Line2D>();

    public DrawPanel() { }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for(final Line2D l : lines) {
            g2d.draw(l);
        }
    }

    public void setArguments(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        addLine();
    }

    public void addLine() {
        lines.add(new Line2D.Double(startX, startY, endX, endY));
    }

    public Line2D getLastLine(ArrayList<Line2D> lines) {
        try {
            return lines.get(lines.size() - 1);
        } catch(NullPointerException npe) {
            return null;
        }
    }

    /** Clear all array lists that contain graphics. */
    public void clearAll() {
        lines.clear();
    }
}
