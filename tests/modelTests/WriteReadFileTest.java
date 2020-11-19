package modelTests;

import model.EllipseModel;
import model.IShapeModel;
import model.ReadFromFile;
import model.RectangleModel;
import model.WriteToFile;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WriteReadFileTest {

    /** Contains the models. */
    private ArrayList<IShapeModel> models;
    /** A regular ellipse model. */
    private EllipseModel ellipseModel;
    /** A regular rectangle model. */
    private RectangleModel rectangleModel;
    /** Name of our test file. */
    private String filename;
    /** Name of a file that does not exist. */
    private String noFilename;

    /** Set up a few new models, add them to the array lists and declare the file name strings. */
    @Before
    public void setup() {

        // Create array list that will hold the models
        models = new ArrayList<>();

        // Create new ellipse model and set properties
        ellipseModel = new EllipseModel();
        ellipseModel.setStartCoordinates(7, 7);
        ellipseModel.setEndCoordinates(42, 42);
        ellipseModel.setLineColour(Color.BLACK);
        ellipseModel.setStrokeSize(3);

        // Create new rectangle model and set properties
        rectangleModel = new RectangleModel();
        rectangleModel.setStartCoordinates(420, 420);
        rectangleModel.setEndCoordinates(7, 7);
        rectangleModel.setLineColour(Color.BLACK);
        rectangleModel.setStrokeSize(3);

        // Add the ellipse and the rectangle to the models ArrayList
        models.add(ellipseModel);
        models.add(rectangleModel);

        // Set the file name strings
        filename = "test_file.frida";
        noFilename = "not_a_file.frida";
    }

    /** Test whether writing to file really creates the file. */
    @Test
    public void testWritingCreatesFile() throws IOException {
        File file = new File(filename);

        // File shouldn't exist yet
        assertFalse(file.exists());

        WriteToFile.write(models, filename);

        // Now it should exist
        assertTrue(file.exists());
    }

    /** Test whether reading file creates the correct models. */
    @Test
    public void testReadingFileCreatesModels() throws IOException, ClassNotFoundException {
        // First write the file
        WriteToFile.write(models, filename);

        // Read file back in and add the models to array list
        ArrayList<IShapeModel> readModels = ReadFromFile.read(filename);

        // Compare each model to the original ones
        for (int i = 0; i < readModels.size() - 1; i++) {
                IShapeModel readInModel = readModels.get(i);
                IShapeModel model = models.get(i);

                assertArrayEquals(readInModel.getStartCoordinates(), model.getStartCoordinates());
                assertArrayEquals(readInModel.getEndCoordinates(), model.getEndCoordinates());
                assertEquals(readInModel.getLineColour(), model.getLineColour());
                assertEquals(readInModel.getStrokeSize(), model.getStrokeSize());
            }
    }

    /** Test that reading from a non-existing file throws an exception */
    @Test(expected = IOException.class)
    public void testReadingNonExistentFileFails() throws IOException, ClassNotFoundException {

        File file = new File(noFilename);

        // File shouldn't exist
        assertFalse(file.exists());

        // Therefore this should throw an error
        ArrayList<IShapeModel> readModels = ReadFromFile.read(noFilename);
    }

    /** Clean up and delete the file that was created during the tests. */
    @AfterClass
    public static void cleanUp() {
        File file = new File("test_file.frida");
        if (file.exists()) {
            // Delete the file and assert that it was deleted
            assertTrue(file.delete());
        }
    }
}
