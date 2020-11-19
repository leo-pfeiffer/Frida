package modelTests;

import model.EllipseModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;

/** JUnit tests for the Ellipse model.
 * @author 190026921*/
public class EllipseModelTest {

    /** Model on which we perform our tests. */
    private EllipseModel ellipse;

    /** Create a new model instance */
    @Before
    public void setup() {
        ellipse = new EllipseModel();
    }

    /** Test setting the start coordinates works. */
    @Test
    public void testStartCoordinates() {
        assertArrayEquals(new int[]{0, 0}, ellipse.getStartCoordinates());
        int x = 42;
        int y = 7;
        ellipse.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, ellipse.getStartCoordinates());
    }

    /** Test setting the end coordinates works. */
    @Test
    public void testEndCoordinates() {
        assertArrayEquals(new int[]{0, 0}, ellipse.getEndCoordinates());
        int x = 21;
        int y = 3;
        ellipse.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, ellipse.getEndCoordinates());
    }

    /** Test setting the start coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testStartCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, ellipse.getStartCoordinates());
        int x = -42;
        int y = -7;
        ellipse.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, ellipse.getStartCoordinates());
    }

    /** Test setting the end coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testEndCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, ellipse.getEndCoordinates());
        int x = -21;
        int y = -3;
        ellipse.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, ellipse.getEndCoordinates());
    }

    /** Test setting the  line colour. */
    @Test
    public void testLineColour() {
        assertNull(ellipse.getLineColour());
        Color colour = Color.PINK;
        ellipse.setLineColour(colour);
        assertEquals(colour, ellipse.getLineColour());
    }

    /** Test setting the stroke size correctly. */
    @Test
    public void testStrokeSize() {
        assertEquals(0, ellipse.getStrokeSize());
        int size = 3;
        ellipse.setStrokeSize(size);
        assertEquals(size, ellipse.getStrokeSize());
    }

    /** Test setting the stroke size incorrectly should throw IllegalArgumentException. */
    @Test(expected = IllegalArgumentException.class)
    public void testStrokeSizeNegative() {
        assertEquals(0, ellipse.getStrokeSize());
        int size = -1;
        ellipse.setStrokeSize(size);
    }

    /** Test moving the shape. */
    @Test
    public void testMove() {
        // Initial values should be null
        assertNull(ellipse.getMoveStart());
        assertNull(ellipse.getMoveEnd());

        // set the ellipse from (0, 0) to (10, 10)
        ellipse.setStartCoordinates(0, 0);
        ellipse.setEndCoordinates(10, 10);

        // Define coordinates of the move
        // Move 30 - 5 to the right and 40 - 5 up
        int startX = 5;
        int startY = 5;
        int endX = 30;
        int endY = 40;

        // Set the coordinates in the class
        ellipse.setMoveStart(startX, startY);
        ellipse.setMoveEnd(endX, endY);

        // Move coordinates shouls have changed
        assertArrayEquals(new int[]{startX, startY}, ellipse.getMoveStart());
        assertArrayEquals(new int[]{endX, endY}, ellipse.getMoveEnd());

        // Start and end coordinates shouldn't have changed
        assertArrayEquals(new int[]{0, 0}, ellipse.getStartCoordinates());
        assertArrayEquals(new int[]{10, 10}, ellipse.getEndCoordinates());

        // Now move the ellipse
        ellipse.move();

        // Position should have moved
        assertArrayEquals(new int[]{25, 35}, ellipse.getStartCoordinates());
        assertArrayEquals(new int[]{35, 45}, ellipse.getEndCoordinates());
    }

    /** Test that negative elements can't be set as move start */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveStartIncorrectly() {
        int startX = -5;
        int startY = -5;

        // Set the coordinates in the class
        ellipse.setMoveStart(startX, startY);

    }

    /** Test that negative elements can't be set as move end */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveEndIncorrectly() {
        int endX = -30;
        int endY = -40;

        // Set the coordinates in the class
        ellipse.setMoveEnd(endX, endY);
    }

    /** Test update behaviour. */
    @Test
    public void testUpdate() {
        // update() calls setChanged which sets changed to true.
        // Then it calls notifyObservers, which sets changed back to false.
        // Therefore, calling update should not change the value returned by hasChanged().
        assertFalse(ellipse.hasChanged());
        ellipse.update();
        assertFalse(ellipse.hasChanged());
    }

    /** Test locked aspect ratio. */
    @Test
    public void testLockAspect() {
        assertFalse(ellipse.getLockAspect());
        ellipse.setLockAspect(true);
        assertTrue(ellipse.getLockAspect());
    }

    /** Test how the position is set with unlocked aspect ratio. */
    @Test
    public void testPositionUnlockedAspect() {

        // Make sure aspect not locked
        assertFalse(ellipse.getLockAspect());

        // Set start and end coordinates
        ellipse.setStartCoordinates(0,0);
        ellipse.setEndCoordinates(10, 20);

        // Assert that the positions are set accordingly
        assertArrayEquals(new int[] {0, 0, 10, 20}, ellipse.getPosition());
    }

    /** Test how the position is set with locked aspect ratio. */
    @Test
    public void testPositionLockedAspect() {
        // Lock the aspect ratio
        ellipse.setLockAspect(true);

        // Set start and end coordinates
        ellipse.setStartCoordinates(0,0);
        ellipse.setEndCoordinates(10, 20);

        // Assert that the positions are set accordingly (should be a circle now)
        assertArrayEquals(new int[] {0, 0, 10, 10}, ellipse.getPosition());
    }
}
