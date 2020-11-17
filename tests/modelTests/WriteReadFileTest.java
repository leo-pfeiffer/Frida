package modelTests;

import model.EllipseModel;
import model.IShapeModel;
import model.ReadFromFile;
import model.RectangleModel;
import model.WriteToFile;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class WriteReadFileTest {

    private ArrayList<IShapeModel> models;
    private EllipseModel ellipseModel;
    private RectangleModel rectangleModel;
    private String filename;

    @Before
    public void setup() {
        models = new ArrayList<IShapeModel>();
        ellipseModel = new EllipseModel();
        ellipseModel.setStartCoordinates(7, 7);
        ellipseModel.setEndCoordinates(42, 42);
        ellipseModel.setLineColour(Color.BLACK);
        ellipseModel.setStrokeSize(3);

        rectangleModel = new RectangleModel();
        rectangleModel.setStartCoordinates(420, 420);
        rectangleModel.setEndCoordinates(7, 7);
        rectangleModel.setLineColour(Color.BLACK);
        rectangleModel.setStrokeSize(3);

        // Add the ellipse and the rectangle to the models ArrayList
        models.add(ellipseModel);
        models.add(rectangleModel);
    }

    @Test
    public void testWritingCreatesFile() {
        filename = "test_file.frida";
        WriteToFile.write(models, filename);

        File file = new File(filename);
        assertTrue(file.exists());
    }

    @Test
    public void testReadingFileCreatesModels() {
        // First write the file
        filename = "test_file.frida";
        WriteToFile.write(models, filename);

        try {
            ArrayList<IShapeModel> readModels = ReadFromFile.read(filename);
            for (int i = 0; i < readModels.size() - 1; i++) {
                IShapeModel rm = readModels.get(i);
                IShapeModel m = models.get(i);

                assertEquals(rm.getStartCoordinates(), m.getStartCoordinates());
                assertEquals(rm.getEndCoordinates(), m.getEndCoordinates());
                assertEquals(rm.getLineColour(), m.getLineColour());
                assertEquals(rm.getStrokeSize(), m.getStrokeSize());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IOException.class)
    public void testReadingNonExistentFileFails() throws IOException {
        filename = "not_a_file.frida";

        try {
            ArrayList<IShapeModel> readModels = ReadFromFile.read(filename);
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException();
        }
    }

    @AfterClass
    public static void cleanUp() {
        File file = new File("test_file.frida");
        if (file.exists()) {
            file.delete();
        }
    }
}
