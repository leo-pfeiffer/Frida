package modelTests;

import model.HexagonModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

public class HexagonModelTest {

    private HexagonModel hex;

    /** Create a new hexagon model. */
    @Before
    public void setup() {
        this.hex = new HexagonModel();
    }

    /** Test setting the start coordinates works. */
    @Test
    public void testStartCoordinates() {
        assertArrayEquals(new int[]{0, 0}, hex.getStartCoordinates());
        int x = 42;
        int y = 7;
        hex.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, hex.getStartCoordinates());
    }

    /** Test setting the end coordinates works. */
    @Test
    public void testEndCoordinates() {
        assertArrayEquals(new int[]{0, 0}, hex.getEndCoordinates());
        int x = 21;
        int y = 3;
        hex.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, hex.getEndCoordinates());
    }

    /** Test setting the start coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testStartCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, hex.getStartCoordinates());
        int x = -42;
        int y = -7;
        hex.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, hex.getStartCoordinates());
    }

    /** Test setting the end coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testEndCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, hex.getEndCoordinates());
        int x = -21;
        int y = -3;
        hex.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, hex.getEndCoordinates());
    }

    /** Test setting the line colour. */
    @Test
    public void testLineColour() {
        assertNull(hex.getLineColour());
        Color colour = Color.PINK;
        hex.setLineColour(colour);
        assertEquals(colour, hex.getLineColour());
    }

    /** Test setting the fill colour. */
    @Test
    public void testFillColour() {
        assertNull(hex.getFillColour());
        Color colour = Color.RED;
        hex.setFillColour(colour);
        assertEquals(colour, hex.getFillColour());
    }

    /** Test setting the stroke size. */
    @Test
    public void testStrokeSize() {
        assertEquals(0, hex.getStrokeSize());
        int size = 3;
        hex.setStrokeSize(size);
        assertEquals(size, hex.getStrokeSize());
    }

    /** Test setting the stroke size incorrectly. */
    @Test(expected = IllegalArgumentException.class)
    public void testStrokeSizeNegative() {
        assertEquals(0, hex.getStrokeSize());
        int size = -1;
        hex.setStrokeSize(size);
    }

    /** Test moving the hexagon. */
    @Test
    public void testMove() {
        // Initial values should be null
        assertNull(hex.getMoveStart());
        assertNull(hex.getMoveEnd());

        // from (0, 0) to (10, 10)
        hex.setStartCoordinates(0, 0);
        hex.setEndCoordinates(10, 10);

        // Define coordinates of the move
        // Move 30 - 5 to the right and 40 - 5 up
        int startX = 5;
        int startY = 5;
        int endX = 30;
        int endY = 40;

        // Set the coordinates in the class
        hex.setMoveStart(startX, startY);
        hex.setMoveEnd(endX, endY);

        // Move coordinates should have changed
        assertArrayEquals(new int[]{startX, startY}, hex.getMoveStart());
        assertArrayEquals(new int[]{endX, endY}, hex.getMoveEnd());

        // Start and end coordinates shouldn't have changed
        assertArrayEquals(new int[]{0, 0}, hex.getStartCoordinates());
        assertArrayEquals(new int[]{10, 10}, hex.getEndCoordinates());

        // Now move
        hex.move();

        // Position should have moved
        assertArrayEquals(new int[]{25, 35}, hex.getStartCoordinates());
        assertArrayEquals(new int[]{35, 45}, hex.getEndCoordinates());
    }

    /** Test that negative elements can't be set as move start */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveStartIncorrectly() {
        int startX = -5;
        int startY = -5;

        // Set the coordinates in the class
        hex.setMoveStart(startX, startY);

    }

    /** Test that negative elements can't be set as move end */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveEndIncorrectly() {
        int endX = -30;
        int endY = -40;

        // Set the coordinates in the class
        hex.setMoveEnd(endX, endY);
    }

    /** Test the update method. */
    @Test
    public void testUpdate() {
        // update() calls setChanged which sets changed to true.
        // Then it calls notifyObservers, which sets changed back to false.
        // Therefore, calling update should not change the value returned by hasChanged().
        assertFalse(hex.hasChanged());
        hex.update();
        assertFalse(hex.hasChanged());
    }

    /** test getting the coordinates. */
    @Test
    public void testPoints() {

        // set points s.t. mid point is at (100, 100)
        hex.setStartCoordinates(50,50);
        hex.setEndCoordinates(150, 150);

        // Expected x and y coordinates of the hexagon.
        double[] expectedX = {171, 135, 65, 29, 64, 135};
        double[] expectedY = {100, 39, 39, 100, 161, 161};

        int[] resultXint = hex.getXpoints();
        int[] resultYint = hex.getYpoints();

        double[] resultX = new double[expectedX.length];
        double[] resultY = new double[expectedX.length];

        for (int i = 0; i < expectedX.length; i++) { resultX[i] = resultXint[i]; }
        for (int i = 0; i < expectedX.length; i++) { resultY[i] = resultYint[i]; }

        // Allow for difference of 2 due to rounding error in the calculation
        assertArrayEquals(expectedX, resultX, 2);
        assertArrayEquals(expectedY, resultY, 2);
    }

    /** Test getting the corners. */
    @Test
    public void testCorners() {
        int[][] initial = {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}};
        assertArrayEquals(initial, hex.getCorners());

        hex.setStartCoordinates(50,50);
        hex.setEndCoordinates(150, 150);

        double[][] expectedX = {
                {171, 100}, {135, 39}, {65, 39},
                {29, 100}, {64, 161}, {135, 161}
        };

        int[][] resultInt = hex.getCorners();

        for (int i = 0; i < expectedX.length; i++) {
            for (int j = 1; j < 2; j++) {
                assertEquals(expectedX[i][j], resultInt[i][j], 2);
            }
        }
    }

}
