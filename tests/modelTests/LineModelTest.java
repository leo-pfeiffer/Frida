package modelTests;

import model.LineModel;
import org.junit.*;

import java.awt.Color;
import java.util.Observable;

import static org.junit.Assert.*;

public class LineModelTest {

    private LineModel line;

    @Before
    public void setup() {
        line = new LineModel();
    }

    @Test
    public void testStartCoordinates() {
        System.out.println("Testing start coordinates");
        assertArrayEquals(new int[]{0, 0}, line.getStartCoordinates());
        int x = 42;
        int y = 7;
        line.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, line.getStartCoordinates());
    }

    @Test
    public void testEndCoordinates() {
        System.out.println("Testing end coordinates");
        assertArrayEquals(new int[]{0, 0}, line.getEndCoordinates());
        int x = 21;
        int y = 3;
        line.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, line.getEndCoordinates());
    }

    @Test
    public void testLineColour() {
        System.out.println("Testing line colour");
        assertNull(line.getLineColour());
        Color colour = Color.PINK;
        line.setLineColour(colour);
        assertEquals(colour, line.getLineColour());
    }

    @Test
    public void testStrokeSize() {
        System.out.println("Testing stroke size");
        assertEquals(0, line.getStrokeSize());
        int size = 3;
        line.setStrokeSize(size);
        assertEquals(size, line.getStrokeSize());
    }

    @Test
    public void testMove() {
        // Initial values should be null
        assertNull(line.getMoveStart());
        assertNull(line.getMoveEnd());

        // set the line from (0, 0) to (10, 10)
        line.setStartCoordinates(0, 0);
        line.setEndCoordinates(10, 10);

        // Define coordinates of the move
        // Move 30 - 5 to the right and 40 - 5 up
        int startX = 5;
        int startY = 5;
        int endX = 30;
        int endY = 40;

        // Set the coordinates in the class
        line.setMoveStart(startX, startY);
        line.setMoveEnd(endX, endY);

        // Move coordinates shouls have changed
        assertArrayEquals(new int[]{startX, startY}, line.getMoveStart());
        assertArrayEquals(new int[]{endX, endY}, line.getMoveEnd());

        // Start and end coordinates shouldn't have changed
        assertArrayEquals(new int[]{0, 0}, line.getStartCoordinates());
        assertArrayEquals(new int[]{10, 10}, line.getEndCoordinates());

        // Now move the line
        line.move();

        // Position should have moved
        assertArrayEquals(new int[]{25, 35}, line.getStartCoordinates());
        assertArrayEquals(new int[]{35, 45}, line.getEndCoordinates());
    }

    @Test
    public void testUpdate() {
        // update() calls setChanged which sets changed to true.
        // Then it calls notifyObservers, which sets changed back to false.
        // Therefore, calling update should not change the value returned by hasChanged().
        assertFalse(line.hasChanged());
        line.update();
        assertFalse(line.hasChanged());
    }
}
