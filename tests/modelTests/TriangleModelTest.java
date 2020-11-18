package modelTests;

import model.TriangleModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;

public class TriangleModelTest {
    private TriangleModel triangle;

    @Before
    public void setup() {
        this.triangle = new TriangleModel();
    }

    @Test
    public void testStartCoordinates() {
        assertArrayEquals(new int[]{0, 0}, triangle.getStartCoordinates());
        int x = 42;
        int y = 7;
        triangle.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, triangle.getStartCoordinates());
    }

    @Test
    public void testEndCoordinates() {
        assertArrayEquals(new int[]{0, 0}, triangle.getEndCoordinates());
        int x = 21;
        int y = 3;
        triangle.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, triangle.getEndCoordinates());
    }

    @Test
    public void testLineColour() {
        assertNull(triangle.getLineColour());
        Color colour = Color.PINK;
        triangle.setLineColour(colour);
        assertEquals(colour, triangle.getLineColour());
    }

    @Test
    public void testFillColour() {
        assertNull(triangle.getFillColour());
        Color colour = Color.RED;
        triangle.setFillColour(colour);
        assertEquals(colour, triangle.getFillColour());
    }

    @Test
    public void testStrokeSize() {
        assertEquals(0, triangle.getStrokeSize());
        int size = 3;
        triangle.setStrokeSize(size);
        assertEquals(size, triangle.getStrokeSize());
    }

    @Test
    public void testMove() {
        // Initial values should be null
        assertNull(triangle.getMoveStart());
        assertNull(triangle.getMoveEnd());

        // from (0, 0) to (10, 10)
        triangle.setStartCoordinates(0, 0);
        triangle.setEndCoordinates(10, 10);

        // Define coordinates of the move
        // Move 30 - 5 to the right and 40 - 5 up
        int startX = 5;
        int startY = 5;
        int endX = 30;
        int endY = 40;

        // Set the coordinates in the class
        triangle.setMoveStart(startX, startY);
        triangle.setMoveEnd(endX, endY);

        // Move coordinates should have changed
        assertArrayEquals(new int[]{startX, startY}, triangle.getMoveStart());
        assertArrayEquals(new int[]{endX, endY}, triangle.getMoveEnd());

        // Start and end coordinates shouldn't have changed
        assertArrayEquals(new int[]{0, 0}, triangle.getStartCoordinates());
        assertArrayEquals(new int[]{10, 10}, triangle.getEndCoordinates());

        // Now move
        triangle.move();

        // Position should have moved
        assertArrayEquals(new int[]{25, 35}, triangle.getStartCoordinates());
        assertArrayEquals(new int[]{35, 45}, triangle.getEndCoordinates());
    }

    @Test
    public void testUpdate() {
        // update() calls setChanged which sets changed to true.
        // Then it calls notifyObservers, which sets changed back to false.
        // Therefore, calling update should not change the value returned by hasChanged().
        assertFalse(triangle.hasChanged());
        triangle.update();
        assertFalse(triangle.hasChanged());
    }

    @Test
    public void testPoints() {

        // set points s.t. mid point is at (100, 100)
        triangle.setStartCoordinates(100,100);
        triangle.setEndCoordinates(150, 150);

        // Expected x and y coordinates of the hexagon.
        int[] expectedX = {100, 50, 150};
        int[] expectedY = {100, 150, 150};

        // Allow for difference of 2 due to rounding error in the calculation
        assertArrayEquals(expectedX, triangle.getXpoints());
        assertArrayEquals(expectedY, triangle.getYpoints());
    }

    @Test
    public void testCorners() {
        int[][] initial = {{0, 0}, {0, 0}, {0, 0}};
        assertArrayEquals(initial, triangle.getCorners());

        triangle.setStartCoordinates(100, 100);
        triangle.setEndCoordinates(150, 150);

        int[][] expectedX = {{100, 100}, {50, 150}, {150, 150}};

        assertArrayEquals(expectedX, triangle.getCorners());
    }
}
