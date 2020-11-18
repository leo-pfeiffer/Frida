package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class WriteToFile {

    public static void write(ArrayList<IShapeModel> models, String filename) throws IOException {
        String fullName = filename;

        FileOutputStream fos = new FileOutputStream(fullName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(models);
        oos.close();
    }
}
