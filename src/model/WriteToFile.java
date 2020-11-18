package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/** Class that can write an array list of IShapeModel objects to file.
 * @author 190026921 */
public abstract class WriteToFile {

    /** Write the array list of IShapeModel objects to file.
     * @param models Contains the models.
     * @param filename The target filename (including path).
     * @throws IOException if something goes wrong while writing the file. */
    public static void write(ArrayList<IShapeModel> models, String filename) throws IOException {

        // Create file and object output stream
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        // Write the object
        oos.writeObject(models);

        // Close the streams
        oos.close();
        fos.close();
    }
}
