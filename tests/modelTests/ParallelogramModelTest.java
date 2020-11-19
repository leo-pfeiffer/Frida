package modelTests;

import model.ParallelogramModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class ParallelogramModelTest {

    /** Model on which we perform our tests. */
    private ParallelogramModel para;

    /** Create a new parallelogram model. */
    @Before
    public void setup() {
        this.para = new ParallelogramModel();
    }

    /** Test setting the start coordinates works. */
    @Test
    public void testStartCoordinates() {
        assertArrayEquals(new int[]{0, 0}, para.getStartCoordinates());
        int x = 42;
        int y = 7;
        para.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, para.getStartCoordinates());
    }

    /** Test setting the end coordinates works. */
    @Test
    public void testEndCoordinates() {
        assertArrayEquals(new int[]{0, 0}, para.getEndCoordinates());
        int x = 21;
        int y = 3;
        para.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, para.getEndCoordinates());
    }

    /** Test setting the start coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testStartCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, para.getStartCoordinates());
        int x = -42;
        int y = -7;
        para.setStartCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, para.getStartCoordinates());
    }

    /** Test setting the end coordinates incorrectly throws exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testEndCoordinatesIncorrectly() {
        assertArrayEquals(new int[]{0, 0}, para.getEndCoordinates());
        int x = -21;
        int y = -3;
        para.setEndCoordinates(x, y);
        assertArrayEquals(new int[]{x, y}, para.getEndCoordinates());
    }


    /** Test setting and getting the line colour. */
    @Test
    public void testLineColour() {
        assertNull(para.getLineColour());
        Color colour = Color.PINK;
        para.setLineColour(colour);
        assertEquals(colour, para.getLineColour());
    }

    /** Test setting and getting the fill colour. */
    @Test
    public void testFillColour() {
        assertNull(para.getFillColour());
        Color colour = Color.RED;
        para.setFillColour(colour);
        assertEquals(colour, para.getFillColour());
    }

    /** Test setting and getting the stroke size. */
    @Test
    public void testStrokeSize() {
        assertEquals(0, para.getStrokeSize());
        int size = 3;
        para.setStrokeSize(size);
        assertEquals(size, para.getStrokeSize());
    }

    /** Test setting the stroke size incorrectly. */
    @Test(expected = IllegalArgumentException.class)
    public void testStrokeSizeNegative() {
        assertEquals(0, para.getStrokeSize());
        int size = -1;
        para.setStrokeSize(size);
    }

    /** Test moving the parallelogram. */
    @Test
    public void testMove() {
        // Initial values should be null
        assertNull(para.getMoveStart());
        assertNull(para.getMoveEnd());

        // set the ellipse from (0, 0) to (10, 10)
        para.setStartCoordinates(0, 0);
        para.setEndCoordinates(10, 10);

        // Define coordinates of the move
        // Move 30 - 5 to the right and 40 - 5 up
        int startX = 5;
        int startY = 5;
        int endX = 30;
        int endY = 40;

        // Set the coordinates in the class
        para.setMoveStart(startX, startY);
        para.setMoveEnd(endX, endY);

        // Move coordinates should have changed
        assertArrayEquals(new int[]{startX, startY}, para.getMoveStart());
        assertArrayEquals(new int[]{endX, endY}, para.getMoveEnd());

        // Start and end coordinates shouldn't have changed
        assertArrayEquals(new int[]{0, 0}, para.getStartCoordinates());
        assertArrayEquals(new int[]{10, 10}, para.getEndCoordinates());

        // Now move the ellipse
        para.move();

        // Position should have moved
        assertArrayEquals(new int[]{25, 35}, para.getStartCoordinates());
        assertArrayEquals(new int[]{35, 45}, para.getEndCoordinates());
    }

    /** Test that negative elements can't be set as move start */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveStartIncorrectly() {
        int startX = -5;
        int startY = -5;

        // Set the coordinates in the class
        para.setMoveStart(startX, startY);

    }

    /** Test that negative elements can't be set as move end */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveEndIncorrectly() {
        int endX = -30;
        int endY = -40;

        // Set the coordinates in the class
        para.setMoveEnd(endX, endY);
    }

    /** Test update method. */
    @Test
    public void testUpdate() {
        // update() calls setChanged which sets changed to true.
        // Then it calls notifyObservers, which sets changed back to false.
        // Therefore, calling update should not change the value returned by hasChanged().
        assertFalse(para.hasChanged());
        para.update();
        assertFalse(para.hasChanged());
    }

    /** Test setting and getting the points. */
    @Test
    public void testPoints() {

        // top left to bottom right
        para.setStartCoordinates(5,10);
        para.setEndCoordinates(15, 20);

        int[] expectedX1 = {5, 5 + (int) (0.67f * 10), 15, 5 + (int) ((1 - 0.67f) * 10)};
        int[] expectedY1 = {10, 10, 20, 20};

        assertArrayEquals(expectedX1, para.getXpoints());
        assertArrayEquals(expectedY1, para.getYpoints());

        // bottom right to bottom left
        para.setStartCoordinates(15, 20);
        para.setEndCoordinates(5,10);

        assertArrayEquals(expectedX1, para.getXpoints());
        assertArrayEquals(expectedY1, para.getYpoints());

        // top right to bottom left
        para.setStartCoordinates(15,10);
        para.setEndCoordinates(5, 20);

        int[] expectedX2 = {5 + (int) ((1 - 0.67f) * 10), 15, 5 + (int) (0.67f * 10), 5};
        int[] expectedY2 = {10, 10, 20, 20};

        assertArrayEquals(expectedX2, para.getXpoints());
        assertArrayEquals(expectedY2, para.getYpoints());

        // bottom left to top right
        para.setStartCoordinates(5, 20);
        para.setEndCoordinates(15,10);

        assertArrayEquals(expectedX2, para.getXpoints());
        assertArrayEquals(expectedY2, para.getYpoints());
    }

    /** Test calculating and getting the corner coordinates. */
    @Test
    public void testCorners() {
        int[][] initial = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
        assertArrayEquals(initial, para.getCorners());

        // top left to bottom right
        para.setStartCoordinates(5,10);
        para.setEndCoordinates(15, 20);

        int[][] expected1 = {
                {5, 10},
                {5 + (int) (0.67f * 10), 10},
                {15, 20},
                {5 + (int) ((1 - 0.67f) * 10), 20}};
        assertArrayEquals(expected1, para.getCorners());

        // top right to bottom left
        para.setStartCoordinates(15,10);
        para.setEndCoordinates(5, 20);

        int[][] expected2 = {
                {5 + (int) ((1 - 0.67f) * 10), 10},
                {15, 10},
                {5 + (int) (0.67f * 10), 20},
                {5, 20}};
        assertArrayEquals(expected2, para.getCorners());
    }
}
