package view;

/** Simple abstract class that contains the text that is
 * displayed to the user when they click on Help. */
public abstract class HelpText {

    /** Return the help text for the Frida GUI.
     * @return Help text of the Frida GUI. */
    public static String getText(){
        return "" +
                "Welcome to Frida!\n\n" +
                "Drag your mouse over the panel to draw shapes.\n" +
                "You can select different shapes and select a line " +
                "colour, fill colour and line thickness by clicking on the buttons.\n" +
                "To save your drawing, click on File > Save. You can now pick a folder and enter a file name.\n" +
                "Open a file that you've previously saved by clicking on File > Open and selecting the file.\n" +
                "Watch out! This deletes your current drawings, so make sure to save them first.\n\n" +
                "There are a few keyboard shortcut that make it easier to use Frida.\n" +
                "- Press U to undo the last shape.\n" +
                "- Press R to redo what you've just undone.\n" +
                "- Press C to delete all current shapes.\n" +
                "- Press and hold L while you draw an Ellipse or a Rectangle to lock the aspect " +
                "ratio to draw a Circle or a Square.\n" +
                "- Press S to save your current file.\n" +
                "- Press O to open a file.\n" +
                "- Press H to open this help dialog.\n\n" +
                "Enjoy!";
    }
}
