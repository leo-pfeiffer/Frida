package modelTests;

import model.StarModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class StarModelTest {

    private StarModel star;

    /** Create a new star model. */
    @Before
    public void setup() {
        this.star = new StarModel();
    }

    /** Test setting the start coordinates works. */
    @Test
    public void testStartCoordinates() {
        assertArrayEquals(new int[]{0, 0}, star.getStartCoordinates());
        int x = 42;
        int y = 7;
        star.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, star.getStartCoordinates());
    }

    /** Test setting the end coordinates works. */
    @Test
    public void testEndCoordinates() {
        assertArrayEquals(new int[]{0, 0}, star.getEndCoordinates());
        int x = 21;
        int y = 3;
        star.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, star.getEndCoordinates());
    }

    /** Test setting the start coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testStartCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, star.getStartCoordinates());
        int x = -42;
        int y = -7;
        star.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, star.getStartCoordinates());
    }

    /** Test setting the end coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testEndCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, star.getEndCoordinates());
        int x = -21;
        int y = -3;
        star.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, star.getEndCoordinates());
    }

    /** Test setting and getting the line colour. */
    @Test
    public void testLineColour() {
        assertNull(star.getLineColour());
        Color colour = Color.PINK;
        star.setLineColour(colour);
        assertEquals(colour, star.getLineColour());
    }

    /** Test setting and getting the fill colour. */
    @Test
    public void testFillColour() {
        assertNull(star.getFillColour());
        Color colour = Color.RED;
        star.setFillColour(colour);
        assertEquals(colour, star.getFillColour());
    }

    /** Test setting and getting the stroke size. */
    @Test
    public void testStrokeSize() {
        assertEquals(0, star.getStrokeSize());
        int size = 3;
        star.setStrokeSize(size);
        assertEquals(size, star.getStrokeSize());
    }

    /** Test setting the stroke size incorrectly. */
    @Test(expected = IllegalArgumentException.class)
    public void testStrokeSizeNegative() {
        assertEquals(0, star.getStrokeSize());
        int size = -1;
        star.setStrokeSize(size);
    }

    /** Test moving the star model. */
    @Test
    public void testMove() {
        // Initial values should be null
        assertNull(star.getMoveStart());
        assertNull(star.getMoveEnd());

        // from (0, 0) to (10, 10)
        star.setStartCoordinates(0, 0);
        star.setEndCoordinates(10, 10);

        // Define coordinates of the move
        // Move 30 - 5 to the right and 40 - 5 up
        int startX = 5;
        int startY = 5;
        int endX = 30;
        int endY = 40;

        // Set the coordinates in the class
        star.setMoveStart(startX, startY);
        star.setMoveEnd(endX, endY);

        // Move coordinates should have changed
        assertArrayEquals(new int[]{startX, startY}, star.getMoveStart());
        assertArrayEquals(new int[]{endX, endY}, star.getMoveEnd());

        // Start and end coordinates shouldn't have changed
        assertArrayEquals(new int[]{0, 0}, star.getStartCoordinates());
        assertArrayEquals(new int[]{10, 10}, star.getEndCoordinates());

        // Now move
        star.move();

        // Position should have moved
        assertArrayEquals(new int[]{25, 35}, star.getStartCoordinates());
        assertArrayEquals(new int[]{35, 45}, star.getEndCoordinates());
    }

    /** Test that negative elements can't be set as move start */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveStartIncorrectly() {
        int startX = -5;
        int startY = -5;

        // Set the coordinates in the class
        star.setMoveStart(startX, startY);

    }

    /** Test that negative elements can't be set as move end */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveEndIncorrectly() {
        int endX = -30;
        int endY = -40;

        // Set the coordinates in the class
        star.setMoveEnd(endX, endY);
    }

    /** Test update method. */
    @Test
    public void testUpdate() {
        // update() calls setChanged which sets changed to true.
        // Then it calls notifyObservers, which sets changed back to false.
        // Therefore, calling update should not change the value returned by hasChanged().
        assertFalse(star.hasChanged());
        star.update();
        assertFalse(star.hasChanged());
    }

    /** Test calculation and getting of the coordinates. */
    @Test
    public void testPoints() {

        // set points s.t. mid point is at (100, 100)
        star.setStartCoordinates(50,50);
        star.setEndCoordinates(150, 150);

        // Expected x and y coordinates of the staragon.
        double[] expectedX = {121, 141, 100, 58, 78, 33, 86, 99, 113, 166};
        double[] expectedY = {107, 156, 123, 156, 107, 78, 81, 30, 81, 78};

        int[] resultXint = star.getXpoints();
        int[] resultYint = star.getYpoints();

        double[] resultX = new double[expectedX.length];
        double[] resultY = new double[expectedX.length];

        for (int i = 0; i < expectedX.length; i++) { resultX[i] = resultXint[i]; }
        for (int i = 0; i < expectedX.length; i++) { resultY[i] = resultYint[i]; }

        // Allow for difference of 2 due to rounding error in the calculation
        assertArrayEquals(expectedX, resultX, 2);
        assertArrayEquals(expectedY, resultY, 2);
    }

    /** Test calculating and getting the corner coordinates. */
    @Test
    public void testCorners() {
        int[][] initial = {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0},
                {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}};
        assertArrayEquals(initial, star.getCorners());

        star.setStartCoordinates(50,50);
        star.setEndCoordinates(150, 150);

        double[][] expectedX = {{121, 107}, {141, 156}, {100, 123},
                {58, 156}, {78, 107}, {33, 78}, {86, 81}, {99, 30},
                {113, 81}, {166, 78}};

        int[][] resultInt = star.getCorners();

        for (int i = 0; i < expectedX.length; i++) {
            for (int j = 1; j < 2; j++) {
                assertEquals(expectedX[i][j], resultInt[i][j], 2);
            }
        }
    }
}
