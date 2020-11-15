package model;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public abstract class ReadFromFile {

    public static ArrayList<IShapeModel> read (String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<IShapeModel> models = (ArrayList<IShapeModel>) ois.readObject();
        ois.close();
        return models;
    }
}
