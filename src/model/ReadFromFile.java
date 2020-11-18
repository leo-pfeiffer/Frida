package model;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/** Class that can read an array list of IShapeModel objects from file.
 * @author 190026921 */
public abstract class ReadFromFile {

    /** Read a file that contains an IShapeModel objects.
     * @param filename The target filename (including path).
     * @throws IOException if something goes wrong while reading the file.
     * @throws ClassNotFoundException if the classes of the object to be read are not found.
     * @return Array list that contains the models. */
    public static ArrayList<IShapeModel> read (String filename) throws IOException, ClassNotFoundException {

        // Open file and object input streams
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);

        // Read the objects from file and put them into array list
        ArrayList<IShapeModel> models = (ArrayList<IShapeModel>) ois.readObject();

        // Close streams
        ois.close();
        fis.close();

        // return the models
        return models;
    }
}
