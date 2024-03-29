package modelTests;

import model.RectangleModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/** Tests for the rectangle model.
 * @author 190026921 */
public class RectangleModelTest {

    /** Model on which we perform our tests. */
    private RectangleModel rectangle;

    /** Create a new rectangle. */
    @Before
    public void setup() {
        this.rectangle = new RectangleModel();
    }

    /** Test setting the start coordinates works. */
    @Test
    public void testStartCoordinates() {
        assertArrayEquals(new int[]{0, 0}, rectangle.getStartCoordinates());
        int x = 42;
        int y = 7;
        rectangle.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, rectangle.getStartCoordinates());
    }

    /** Test setting the end coordinates works. */
    @Test
    public void testEndCoordinates() {
        assertArrayEquals(new int[]{0, 0}, rectangle.getEndCoordinates());
        int x = 21;
        int y = 3;
        rectangle.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, rectangle.getEndCoordinates());
    }

    /** Test setting the start coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testStartCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, rectangle.getStartCoordinates());
        int x = -42;
        int y = -7;
        rectangle.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, rectangle.getStartCoordinates());
    }

    /** Test setting the end coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testEndCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, rectangle.getEndCoordinates());
        int x = -21;
        int y = -3;
        rectangle.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, rectangle.getEndCoordinates());
    }

    /** Test setting and getting the line colour. */
    @Test
    public void testLineColour() {
        assertNull(rectangle.getLineColour());
        Color colour = Color.PINK;
        rectangle.setLineColour(colour);
        assertEquals(colour, rectangle.getLineColour());
    }

    /** Test setting and getting the fill colour. */
    @Test
    public void testFillColour() {
        assertNull(rectangle.getFillColour());
        Color colour = Color.RED;
        rectangle.setFillColour(colour);
        assertEquals(colour, rectangle.getFillColour());
    }

    /** Test setting and getting the stroke size. */
    @Test
    public void testStrokeSize() {
        assertEquals(0, rectangle.getStrokeSize());
        int size = 3;
        rectangle.setStrokeSize(size);
        assertEquals(size, rectangle.getStrokeSize());
    }

    /** Test setting the stroke size incorrectly. */
    @Test(expected = IllegalArgumentException.class)
    public void testStrokeSizeNegative() {
        assertEquals(0, rectangle.getStrokeSize());
        int size = -1;
        rectangle.setStrokeSize(size);
    }

    /** Test moving the rectangle. */
    @Test
    public void testMove() {
        // Initial values should be null
        assertNull(rectangle.getMoveStart());
        assertNull(rectangle.getMoveEnd());

        // set the ellipse from (0, 0) to (10, 10)
        rectangle.setStartCoordinates(0, 0);
        rectangle.setEndCoordinates(10, 10);

        // Define coordinates of the move
        // Move 30 - 5 to the right and 40 - 5 up
        int startX = 5;
        int startY = 5;
        int endX = 30;
        int endY = 40;

        // Set the coordinates in the class
        rectangle.setMoveStart(startX, startY);
        rectangle.setMoveEnd(endX, endY);

        // Move coordinates shouls have changed
        assertArrayEquals(new int[]{startX, startY}, rectangle.getMoveStart());
        assertArrayEquals(new int[]{endX, endY}, rectangle.getMoveEnd());

        // Start and end coordinates shouldn't have changed
        assertArrayEquals(new int[]{0, 0}, rectangle.getStartCoordinates());
        assertArrayEquals(new int[]{10, 10}, rectangle.getEndCoordinates());

        // Now move the ellipse
        rectangle.move();

        // Position should have moved
        assertArrayEquals(new int[]{25, 35}, rectangle.getStartCoordinates());
        assertArrayEquals(new int[]{35, 45}, rectangle.getEndCoordinates());
    }

    /** Test that negative elements can't be set as move start */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveStartIncorrectly() {
        int startX = -5;
        int startY = -5;

        // Set the coordinates in the class
        rectangle.setMoveStart(startX, startY);

    }

    /** Test that negative elements can't be set as move end */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveEndIncorrectly() {
        int endX = -30;
        int endY = -40;

        // Set the coordinates in the class
        rectangle.setMoveEnd(endX, endY);
    }

    /** Test update method. */
    @Test
    public void testUpdate() {
        // update() calls setChanged which sets changed to true.
        // Then it calls notifyObservers, which sets changed back to false.
        // Therefore, calling update should not change the value returned by hasChanged().
        assertFalse(rectangle.hasChanged());
        rectangle.update();
        assertFalse(rectangle.hasChanged());
    }

    /** Test setting and getting the lock of aspect ratio. */
    @Test
    public void testLockAspect() {
        assertFalse(rectangle.getLockAspect());
        rectangle.setLockAspect(true);
        assertTrue(rectangle.getLockAspect());
    }

    /** Test calculation of the coordinates if aspect ratio is unlocked. */
    @Test
    public void testPointsUnlockedAspect() {

        // Make sure aspect not locked
        assertFalse(rectangle.getLockAspect());

        // Set start and end coordinates
        rectangle.setStartCoordinates(0,0);
        rectangle.setEndCoordinates(10, 20);

        // Assert that the positions are set accordingly
        assertArrayEquals(new int[] {0, 10, 10, 0}, rectangle.getXpoints());
        assertArrayEquals(new int[] {0, 0, 20, 20}, rectangle.getYpoints());
    }

    /** Test calculation of the coordinates if aspect ratio is locked. */
    @Test
    public void testPointsLockedAspect() {
        // Lock the aspect ratio
        rectangle.setLockAspect(true);

        // Set start and end coordinates
        rectangle.setStartCoordinates(0,0);
        rectangle.setEndCoordinates(10, 20);

        // Assert that the positions are set accordingly (should be a square now)
        assertArrayEquals(new int[] {0, 20, 20, 0}, rectangle.getXpoints());
        assertArrayEquals(new int[] {0, 0, 20, 20}, rectangle.getYpoints());
    }

    /** Test calculating and getting the corner coordinates. */
    @Test
    public void testCorners() {
        int[][] initial = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
        assertArrayEquals(initial, rectangle.getCorners());

        rectangle.setStartCoordinates(5,10);
        rectangle.setEndCoordinates(15, 20);

        int[][] expected = {{5, 10}, {15, 10}, {15, 20}, {5, 20}};
        assertArrayEquals(expected, rectangle.getCorners());
    }

}
