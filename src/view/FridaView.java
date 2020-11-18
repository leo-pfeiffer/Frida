package view;

// Controllers
import controller.IShapeController;
import controller.ShapeController;
import controller.Shape2DController;

// models
import model.EllipseModel;
import model.HexagonModel;
import model.IShapeModel;
import model.LineModel;
import model.ParallelogramModel;
import model.RectangleModel;
import model.ShapeModel2D;
import model.StarModel;
import model.TriangleModel;

// java swing
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

// java awt
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// java util and IO
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

/** Class that defines the GUI view of the Frida programme.
 * The different panels, buttons etc. are set up and the interaction with the user is defined. */
public class FridaView implements Observer {

    // Models, controllers, etc.
    /** Contains all active controllers. */
    private ArrayList<IShapeController> controllers = new ArrayList<>();
    /** The currently active model. */
    private IShapeModel activeModel;

    // Constants
    /** Default width of the main frame. */
    private static final int DEFAULT_FRAME_WIDTH = 1200;
    /** Default height of the main frame. */
    private static final int DEFAULT_FRAME_HEIGHT = 800;
    /** Default width of the draw panel. */
    private static final int DRAW_PANEL_WIDTH = 1200;
    /** Default height of the draw panel. */
    private static final int DRAW_PANEL_HEIGHT = 600;
    /** Default background of the draw panel. */
    private static final Color DRAW_BACKGROUND_COLOUR = Color.WHITE;

    // View elements
    /** The main JFrame holding all other components. */
    private JFrame mainFrame;
    /** This is the panel we will be drawing on. */
    private DrawPanel drawPanel;
    /** File menu for saving, loading etc. */
    private JMenuBar fileMenu;
    /** Toolbox to switch shapes etc. */
    private JToolBar toolbox;

    // Buttons
    /** Button to activate line mode. */
    private JButton lineButton;
    /** Button to activate rectangle mode. */
    private JButton rectangleButton;
    /** Button to parallelogram line mode. */
    private JButton parallelogramButton;
    /** Button to activate triangle mode. */
    private JButton triangleButton;
    /** Button to activate hexagon mode. */
    private JButton hexagonButton;
    /** Button to activate ellipse mode. */
    private JButton ellipseButton;
    /** Button to activate star mode. */
    private JButton starButton;
    /** Button to activate move mode. */
    private JButton moveButton;
    /** Button to pick a new line colour. */
    private ColourPicker lineColourPicker;
    /** Button to pick a new fill colour */
    private ColourPicker fillColourPicker;
    /** Stroke Styler to let the user pick a stroke size. */
    private StrokeStyler strokeStyler;

    /** A list containing all buttons. */
    private ArrayList<JButton> allButtons = new ArrayList<>();

    /** Start coordinates of a mouse movement. */
    private int[] start = new int[2];
    /** End coordinates of a mouse movement. */
    private int[] end = new int[2];

    /** If true, lock aspect ratio. */
    private boolean lockAspect = false;

    /** If true, user can't draw shapes but can move existing ones. */
    private boolean moveMode = false;

    /** True if the user is currently in the process of moving an object, i.e. has the mouse pressed down and
     * is dragging an object across the panel. */
    private boolean moving = false;

    /** Action to clear the panel. */
    Action clearAction;
    /** Action to undo last shape. */
    Action undoAction;
    /** Action to redo last undone shape. */
    Action redoAction;
    /** Action to lock the aspect ratio. */
    Action lockAspectAction;
    /** Action to unlock the aspect ratio. */
    Action unlockAspectAction;
    /** Action to save the drawing. */
    Action saveAction;
    /** Action open a file. */
    Action openAction;
    /** Action to open the help dialog. */
    Action helpAction;

    /** Construct a new object by setting up the components. */
    public FridaView() {

        // Create the main frame for the view
        mainFrame = new JFrame("Frida");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        mainFrame.setVisible(true);

        // Create actions
        createActions();

        // Create the control and draw panels
        drawPanel = new DrawPanel(this);

        // Create the menus and toolbox
        fileMenu = new JMenuBar();
        toolbox = new JToolBar();

        // Setup the components
        this.setupComponents();

        // Initially, we use the line model
        activeModel = new LineModel();

        // add observer, add model to models, add controller to controllers
        setupNewModel(new ShapeController((LineModel) activeModel));

        // Paint and pack the main frame and its components
        mainFrame.paintAll(mainFrame.getGraphics());
        mainFrame.pack();
    }

    /** Create all actions. */
    public void createActions() {

        // Locking the aspect ratio
        lockAspectAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lockAspect = true;
            }
        };

        // Unlocking the aspect ratio
        unlockAspectAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lockAspect = false;
            }
        };

        // Opening a file
        openAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Open a file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open");

                int returnVal = fileChooser.showOpenDialog(fileChooser);

                // If user chooses file and clicks OK, read the file
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    drawPanel.readFromFile(selectedFile.toString());
                }
                mainFrame.repaint();
            }
        };

        // Save the drawing
        saveAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Open a file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save");

                int userSelection = fileChooser.showSaveDialog(fileChooser);

                // If user has entered a name and clicked OK, write the shapes to a file
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File saveFile = fileChooser.getSelectedFile();
                    drawPanel.writeToFile(saveFile.toString());
                }
            }
        };

        // Open the help dialog
        helpAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, HelpText.getText(), "Help",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };

        // Redo last undone shape
        redoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.redo();
                mainFrame.repaint();
            }
        };

        // Undo last shape
        undoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.undo();
                mainFrame.repaint();
            }
        };

        // Clear the current drawing panel.
        clearAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.clearAll();
                mainFrame.repaint();
            }
        };
    }

    /** Setup the different components of the main frame, i.e. panel, toolbox, menu,
     * and add them to the main frame. */
    public void setupComponents() {
        this.setupPanel();
        this.setupMenus();
        this.setupToolbox();
    }

    /** Setup the draw panel */
    public void setupPanel() {

        // Set foreground colour and make it opaque
        this.drawPanel.setForeground(DRAW_BACKGROUND_COLOUR);
        this.drawPanel.setOpaque(true);

        // Set the preferred size of the draw panel.
        this.drawPanel.setPreferredSize(new Dimension(DRAW_PANEL_WIDTH, DRAW_PANEL_HEIGHT));

        // add a MouseListener that listens for clicks and releases
        this.addMouseListenerToDrawPanel();

        // Add a motion listener to listen for mouse drags
        this.addMouseMotionListenerToDrawPanel();

        // Add input and action maps
        this.addActionAndInputMapsToDrawPanel();

        // add the drawPanel to the center of the main frame
        mainFrame.add(drawPanel, BorderLayout.SOUTH);
    }

    /** Add actions to certain keys. */
    private void addActionAndInputMapsToDrawPanel() {

        // Clear
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("C"), "clear");

        this.drawPanel.getActionMap().put("clear", clearAction);

        // Undo
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("U"), "undo");

        this.drawPanel.getActionMap().put("undo", undoAction);

        // Redo
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("R"), "redo");

        this.drawPanel.getActionMap().put("redo", redoAction);

        // Help
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("H"), "help");

        this.drawPanel.getActionMap().put("help", helpAction);

        // Save
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("S"), "save");

        this.drawPanel.getActionMap().put("save", saveAction);

        // Load
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("O"), "open");

        this.drawPanel.getActionMap().put("open", openAction);

        // Lock and unlock aspect ratio
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("L"), "lock aspect");
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("released L"), "unlock aspect");

        this.drawPanel.getActionMap().put("lock aspect", lockAspectAction);
        this.drawPanel.getActionMap().put("unlock aspect", unlockAspectAction);
    }

    /** Set up the menu bar. */
    public void setupMenus() {
        // Set up file and edit menus
        JMenu file = new JMenu ("File");
        JMenu edit = new JMenu ("Edit");

        // Create the items
        JMenuItem open = new JMenuItem ("Open");
        JMenuItem save = new JMenuItem ("Save");
        JMenuItem help = new JMenuItem ("Help");

        JMenuItem undo = new JMenuItem ("Undo");
        JMenuItem redo = new JMenuItem ("Redo");
        JMenuItem clear = new JMenuItem ("Clear");

        // Add items to the menu
        file.add (open);
        file.add (save);
        file.add (help);

        edit.add (undo);
        edit.add (redo);
        edit.add (clear);

        // Add menus to the overall menu
        fileMenu.add(file);
        fileMenu.add(edit);

        // Add action listeners and actions to the items
        addActionListenerToMenuItem(open, openAction);
        addActionListenerToMenuItem(save, saveAction);
        addActionListenerToMenuItem(help, helpAction);

        addActionListenerToMenuItem(undo, undoAction);
        addActionListenerToMenuItem(redo, redoAction);
        addActionListenerToMenuItem(clear, clearAction);

        // Attach the menu bar to the main frame
        mainFrame.setJMenuBar(fileMenu);
        mainFrame.setJMenuBar(fileMenu);
    }

    /** Add an action listener and an action to a JMenuItem.
     * @param item the JMenuItem.
     * @param action the action to be attached. */
    public void addActionListenerToMenuItem(JMenuItem item, Action action) {
        item.addActionListener(action);
    }

    /** Set up all components of the toolbox, add the ActionListeners and
     * add them to the toolbox. */
    public void setupToolbox() {

        // Create the components
        strokeStyler = new StrokeStyler("Stroke Style");
        allButtons.add(strokeStyler);

        lineColourPicker = new ColourPicker("Line Colour", Color.BLACK);
        allButtons.add(lineColourPicker);

        fillColourPicker = new ColourPicker("Fill Colour", drawPanel.getBackground());
        allButtons.add(fillColourPicker);

        moveButton = new JButton("Move");
        allButtons.add(moveButton);

        lineButton = new JButton("Line");
        allButtons.add(lineButton);

        rectangleButton = new JButton("Rectangle");
        allButtons.add(rectangleButton);

        parallelogramButton = new JButton("Parallelogram");
        allButtons.add(parallelogramButton);

        triangleButton = new JButton("Triangle");
        allButtons.add(triangleButton);

        hexagonButton = new JButton("Hexagon");
        allButtons.add(hexagonButton);

        ellipseButton = new JButton("Ellipse");
        allButtons.add(ellipseButton);

        starButton = new JButton("Star");
        allButtons.add(starButton);

        // Add ActionListeners to all buttons, set their margin and add them to the toolbox
        for (JButton button : allButtons) {
            // add ActionListeners
            addActionListenerToButton(button);

            // Set margins
            if (!(button instanceof StrokeStyler)) {
                button.setMargin(new Insets(7, 4, 7, 4));
            }

            // Add buttons to toolbox
            toolbox.add(button);
        }

        // add toolbox to the top of the main frame
        mainFrame.add(toolbox, BorderLayout.NORTH);

        // Initially, make lineButton active as the default model is also the LineModel
        activateButton(lineButton);
    }

    /** Add the appropriate ActionListener to the button and set the behaviour
     * for actionPerformed.
     * @param button The button the ActionListener is added to. */
    public void addActionListenerToButton(JButton button) {
        button.addActionListener(e -> {

            // Define the appropriate action upon actionPerformed for each button
            switch (button.getText()) {
                case "Move" -> activateButton(button);
                case "Line" -> {
                    activateButton(button);
                    activeModel = new LineModel();
                }
                case "Rectangle" -> {
                    activateButton(button);
                    activeModel = new RectangleModel();
                }
                case "Parallelogram" -> {
                    activateButton(button);
                    activeModel = new ParallelogramModel();
                }
                case "Triangle" -> {
                    activateButton(button);
                    activeModel = new TriangleModel();
                }
                case "Hexagon" -> {
                    activateButton(button);
                    activeModel = new HexagonModel();
                }
                case "Ellipse" -> {
                    activateButton(button);
                    activeModel = new EllipseModel();
                }
                case "Star" -> {
                    activateButton(button);
                    activeModel = new StarModel();
                }
            }
        });
    }

    /** Add a mouse listener to the drawing panel that listens to the user's mouse clicks. */
    public void addMouseListenerToDrawPanel() {
        this.drawPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                // end a line
                Point point = e.getPoint();

                // If we're not in move mode, send the end point to the controller as EndCoordinates
                if (!moveMode) {
                    setEnd(point);
                    if (activeModel instanceof EllipseModel | activeModel instanceof RectangleModel) {
                        getCurrentController().setLockAspect(lockAspect);
                    }
                    getCurrentController().setEndCoordinates(end[0], end[1]);
                }

                // In move mode, send the end point to the controller as MoveEnd
                else {
                    // Only do this if the user is actually dragging a shape
                    if (moving) {
                        // set the end point of the move
                        getCurrentController().setMoveEnd((int) point.getX(), (int) point.getY());

                        // move the model
                        getCurrentController().move();

                        // Save current end point as start point for next move
                        getCurrentController().setMoveStart((int) point.getX(), (int) point.getY());

                        // Done moving.
                        moving = false;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // start a line
                Point point = e.getPoint();

                // If we're not in move mode, set point as StartCoordinates
                if (!moveMode) {
                    setStart(point);
                    createNewModel();
                    getCurrentController().setStartCoordinates(start[0], start[1]);
                }

                // In move mode, get the model at the point where the user clicked and get it ready to move.
                else {
                    moving = false;
                    IShapeModel modelOnPoint = drawPanel.getModelOnPoint(point);

                    // If there's actually a shape where the user clicked
                    if (modelOnPoint != null) {

                        // reset the activeModel to the one selected by the user
                        activeModel = modelOnPoint;
                        moving = true;

                        // Get index of model in the models array list
                        int ind = drawPanel.getModels().indexOf(activeModel);

                        drawPanel.removeModelAndShapeOnIndex(ind);

                        // Move model and controller at end of array list
                        controllers.add(controllers.get(ind));
                        controllers.remove(ind);

                        // Re-add the model to be moved to the drawing panel
                        drawPanel.addModel(activeModel);

                        // Set the starting point of the move
                        getCurrentController().setMoveStart((int) point.getX(), (int) point.getY());
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    /** Add a mouse motion listener to the drawing panel that listens to the user's mouse movements. */
    private void addMouseMotionListenerToDrawPanel() {
        this.drawPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getPoint();

                // If we're not in move mode, update the EndCoordinates of the model
                if (!moveMode) {
                    // For ellipses and rectangles, consider whether the aspect ratio is locked
                    if (activeModel instanceof EllipseModel | activeModel instanceof RectangleModel) {
                        getCurrentController().setLockAspect(lockAspect);
                    }
                    setEnd(point);
                    getCurrentController().setEndCoordinates(end[0], end[1]);
                }

                // If in move mode, actually move the shape
                else {
                    // Must have a shape selected to be able to move.
                    if (moving) {
                        // set the end point of the move
                        getCurrentController().setMoveEnd((int) point.getX(), (int) point.getY());
                        // Move the model
                        getCurrentController().move();
                        // Save current end point as start point for next move
                        getCurrentController().setMoveStart((int) point.getX(), (int) point.getY());
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });
    }

    /** Get the class itself in order to use it in anonymous inner classes.
     * @return the reference to the current instance. */
    public FridaView getOuterThis() {
        return this;
    }

    /** Method to bundle the functions required to set up a new model that
     * is added when the user creates a new shape on the drawing panel.
     * @param controller The controller that will be added with the new model. */
    public void setupNewModel(IShapeController controller) {

        // Add observer to new instance
        ((Observable) activeModel).addObserver(getOuterThis());

        // add controller
        controllers.add(controller);

        // add to draw panel
        drawPanel.addModel(activeModel);
    }

    /** This is called automatically whenever one of the models notifies the Observer (this class) of a change.
     * @param arg used for internal purposes. */
    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(() -> {

            if (!moveMode) {
                // If we draw a 2D shape, apply a fill colour
                if (activeModel instanceof ShapeModel2D) {
                    // ((ShapeModel2D) activeModel).setFillColour(fillColourPicker.getColour());
                    ((Shape2DController) getCurrentController()).setFillColour(fillColourPicker.getColour());
                }

                // Set the model colour to the current state of the colour picker
                // activeModel.setLineColour(lineColourPicker.getColour());
                getCurrentController().setLineColour(lineColourPicker.getColour());
                getCurrentController().setStrokeSize(strokeStyler.getStrokeSize());
            }

            // Update the position of the shape.
            drawPanel.updateLastShape();

            // repaint
            mainFrame.repaint();
        });
    }

    /** Marks one button in the toolbox as active (bold & black) and all others
     * as inactive (plain & dark gray).
     * @param button The button to be activated. */
    public void activateButton(JButton button){

        // Determine if the button to be activated is the move button and if so set the moveMode to true.
        moveMode = button.getText().equals("Move");

        for (JButton butt : allButtons) {
            // Set button layout to active for the specified button
            if (butt.equals(button)) {
                Font bold = butt.getFont().deriveFont(Font.BOLD);
                butt.setFont(bold);
                butt.setForeground(Color.BLACK);
            }
            // Set button layout to inactive for all other buttons
            else {
                Font plain = butt.getFont().deriveFont(Font.PLAIN);
                butt.setFont(plain);
                butt.setForeground(Color.DARK_GRAY);
            }
        }
    }

    /** Set the start point of a mouse movement.
     * @param point Point where the movement started. */
    public void setStart(Point point) {
        this.start[0] = (int) point.getX();
        this.start[1] = (int) point.getY();
    }

    /** Set the end point of a mouse movement.
     * @param point Point where the movement ended. */
    public void setEnd(Point point) {
        this.end[0] = (int) point.getX();
        this.end[1] = (int) point.getY();
    }

    /** After opening a file containing a model, this function adds appropriate controllers and observers to this model.
     * @param model the model to be added and to add an observer & controller to */
    public void addModelFromFile(IShapeModel model) {
        // add an observer
        ((Observable) model).addObserver(getOuterThis());

        // New line
        if (model instanceof LineModel) {
            controllers.add(new ShapeController((LineModel) model));
        }

        // New rectangle
        else if (model instanceof RectangleModel & !(model instanceof EllipseModel)) {
            controllers.add(new Shape2DController((RectangleModel) model));
        }

        // New triangle
        else if (model instanceof TriangleModel) {
            controllers.add(new Shape2DController((TriangleModel) model));
        }

        // New parallelogram
        else if (model instanceof ParallelogramModel) {
            controllers.add(new Shape2DController((ParallelogramModel) model));
        }

        // New star
        else if (model instanceof StarModel) {
            ((Observable) model).addObserver(getOuterThis());
            controllers.add(new Shape2DController((StarModel) model));
        }

        // New hexagon
        else if (model instanceof HexagonModel) {
            controllers.add(new Shape2DController((HexagonModel) model));
        }

        // New ellipse
        if (model instanceof EllipseModel) {
            controllers.add(new ShapeController((EllipseModel) model));
        }
    }

    /** Clears all current controllers. */
    public void clearControllers() {
        this.controllers.clear();
    }

    /** Set up a full model from the model that is currently set as the active model. */
    public void createNewModel() {
        // New line
        if (activeModel instanceof LineModel) {
            activeModel = new LineModel();
            setupNewModel(new ShapeController((LineModel) activeModel));
        }

        // New rectangle
        else if (activeModel instanceof RectangleModel
                & !(activeModel instanceof EllipseModel)) {
            activeModel = new RectangleModel();
            setupNewModel(new Shape2DController((RectangleModel) activeModel));
        }

        // New triangle
        else if (activeModel instanceof TriangleModel) {
            activeModel = new TriangleModel();
            setupNewModel(new Shape2DController((TriangleModel) activeModel));
        }

        // New parallelogram
        else if (activeModel instanceof ParallelogramModel) {
            activeModel = new ParallelogramModel();
            setupNewModel(new Shape2DController((ParallelogramModel) activeModel));
        }

        // New star
        else if (activeModel instanceof StarModel) {
            activeModel = new StarModel();
            setupNewModel(new Shape2DController((StarModel) activeModel));
        }

        // New hexagon
        else if (activeModel instanceof HexagonModel) {
            activeModel = new HexagonModel();
            setupNewModel(new Shape2DController((HexagonModel) activeModel));
        }

        // New ellipse
        if (activeModel instanceof EllipseModel) {
            activeModel = new EllipseModel();
            setupNewModel(new ShapeController((EllipseModel) activeModel));
        }
    }

    /** Get the last (i.e. current) element in the controllers list.
     * @return Current controller. */
    public IShapeController getCurrentController() {
        return controllers.get(controllers.size() - 1);
    }
}