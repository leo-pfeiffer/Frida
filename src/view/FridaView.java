package view;

import controller.IShapeController;
import controller.ShapeController;
import controller.Shape2DController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

public class FridaView implements Observer, ActionListener {

    /** Contains all active models. */
    private ArrayList<IShapeModel> models = new ArrayList<>();
    /** Contains all active controllers. */
    private ArrayList<IShapeController> controllers = new ArrayList<>();
    /** The currently active model. */
    private IShapeModel activeModel;

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
    /** Editing menu for undoing etc. */
    private JMenuBar editMenu;
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

    private StrokeStyler strokeStyler;

    /** A list containing all buttons. */
    private ArrayList<JButton> allButtons = new ArrayList<>();

    /** Start coordinates. */
    private int[] start = new int[2];
    /** Start coordinates. */
    private int[] end = new int[2];

    /** If true, lock aspect ratio. */
    private boolean lockAspect = false;

    private boolean moveMode = false;
    private boolean moving = false;

    Action clearAction;
    Action undoAction;
    Action redoAction;
    Action lockAspectAction;
    Action unlockAspectAction;
    Action saveAction;
    Action openAction;

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
        drawPanel = new DrawPanel();

        // Create the menus and toolbox
        fileMenu = new JMenuBar();
        editMenu = new JMenuBar();
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

    public void createActions() {
        lockAspectAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lockAspect = true;
            }
        };

        unlockAspectAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lockAspect = false;
            }
        };

        openAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open");
                int returnVal = fileChooser.showOpenDialog(fileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    drawPanel.readFromFile(selectedFile.toString());
                }
                mainFrame.repaint();
            }
        };

        saveAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save");

                int userSelection = fileChooser.showSaveDialog(fileChooser);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File saveFile = fileChooser.getSelectedFile();
                    drawPanel.writeToFile(saveFile.toString());
                }

            }
        };

        redoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.redo();
                mainFrame.repaint();
            }
        };

        undoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.undo();
                mainFrame.repaint();
            }
        };

        clearAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.clearAll();
                mainFrame.repaint();
            }
        };
    }

    /** Method to setup the different components of the main frame, i.e. toolbox and menu,
     * and add them to the main frame. */
    public void setupComponents() {
        this.setupPanel();
        this.setupFileMenu();
        this.setupEditMenu();
        this.setupToolbox();
    }

    /** Setup the draw panel */
    public void setupPanel() {

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

        // Save
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("S"), "save");

        this.drawPanel.getActionMap().put("save", saveAction);

        // Load
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("O"), "open");

        this.drawPanel.getActionMap().put("open", openAction);

        // Lock Aspect Ratio
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("L"), "lock aspect");
        this.drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("released L"), "unlock aspect");

        this.drawPanel.getActionMap().put("lock aspect", lockAspectAction);
        this.drawPanel.getActionMap().put("unlock aspect", unlockAspectAction);
    }

    public void setupFileMenu() {
        // Fill menu bar
        JMenu file = new JMenu ("File");
        JMenuItem open = new JMenuItem ("Open");
        JMenuItem save = new JMenuItem ("Save");
        JMenuItem help = new JMenuItem ("Help");
        file.add (open);
        file.add (save);
        file.add (help);
        fileMenu.add (file);

        open.addActionListener(e -> {
            openAction.actionPerformed(e);
        });

        save.addActionListener(e -> {
            saveAction.actionPerformed(e);
        });

        help.addActionListener(e -> {
            // todo call appropriate method in model
            JOptionPane.showMessageDialog(mainFrame, "Help not linked to model!");
        });

        mainFrame.setJMenuBar(fileMenu);
    }

    public void setupEditMenu() {
        // Fill menu bar
        JMenu edit = new JMenu ("Edit");
        JMenuItem undo = new JMenuItem ("Undo");
        JMenuItem redo = new JMenuItem ("Redo");
        JMenuItem clear = new JMenuItem ("Clear");
        edit.add (undo);
        edit.add (redo);
        edit.add (clear);
        fileMenu.add (edit);

        undo.addActionListener(e -> {
            undoAction.actionPerformed(e);
        });

        redo.addActionListener(e -> {
            redoAction.actionPerformed(e);
        });

        clear.addActionListener(e -> {
            clearAction.actionPerformed(e);
        });

        mainFrame.setJMenuBar(fileMenu);
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

        for (JButton b : allButtons) {
            // add ActionListeners
            addActionListenerToButton(b);

            if (!(b instanceof StrokeStyler)) {
                b.setMargin(new Insets(7, 4, 7, 4));
            }

            // Add buttons to toolbox
            toolbox.add(b);
        }

        // add toolbox to the top of the main frame
        mainFrame.add(toolbox, BorderLayout.NORTH);

        // Initially, make lineButton active as the default model is also the LineModel
        activateButton(lineButton);
    }

    /** Add the appropriate ActionListener to the button and set the behaviour
     * for actionPerformed.
     * @param b The button the ActionListener is added to. */
    public void addActionListenerToButton(JButton b) {
        b.addActionListener(e -> {

            // Define the appropriate action upon actionPerformed for each button
            switch (b.getText()) {
                case "Move" -> {
                    activateButton(b);
                }
                case "Line" -> {
                    activateButton(b);
                    activeModel = new LineModel();
                }
                case "Rectangle" -> {
                    activateButton(b);
                    activeModel = new RectangleModel();
                }
                case "Parallelogram" -> {
                    activateButton(b);
                    activeModel = new ParallelogramModel();
                }
                case "Triangle" -> {
                    activateButton(b);
                    activeModel = new TriangleModel();
                }
                case "Hexagon" -> {
                    activateButton(b);
                    activeModel = new HexagonModel();
                }
                case "Ellipse" -> {
                    activateButton(b);
                    activeModel = new EllipseModel();
                }
                case "Star" -> {
                    activateButton(b);
                    activeModel = new StarModel();
                }
            }
        });
    }

    public void addMouseListenerToDrawPanel() {
        this.drawPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                // end a line
                Point point = e.getPoint();

                if (!moveMode) {
                    setEnd(point);
                    if (activeModel instanceof EllipseModel | activeModel instanceof RectangleModel) {
                        getCurrentController().setLockAspect(lockAspect);
                    }
                    getCurrentController().setEndCoordinates(end[0], end[1]);
                }

                else {
                    if (moving) {
                        getCurrentController().setMoveEnd((int) point.getX(), (int) point.getY());
                        getCurrentController().move();
                        getCurrentController().setMoveStart((int) point.getX(), (int) point.getY());
                        moving = false;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // start a line
                Point point = e.getPoint();

                if (!moveMode) {
                    setStart(point);
                    createNewModel();
                    getCurrentController().setStartCoordinates(start[0], start[1]);
                }

                else {
                    moving = false;
                    IShapeModel modelOnPoint = drawPanel.getModelOnPoint(point);
                    if (modelOnPoint != null) {
                        activeModel = modelOnPoint;
                        moving = true;

                        // Get index of model in the models array list
                        int ind = models.indexOf(activeModel);

                        // Move model and controller at end of array list
                        models.add(activeModel);
                        models.remove(ind);
                        controllers.add(controllers.get(ind));
                        controllers.remove(ind);

                        drawPanel.addModel(activeModel);

                        // Get controller
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

    private void addMouseMotionListenerToDrawPanel() {
        this.drawPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getPoint();
                if (!moveMode) {
                    if (activeModel instanceof EllipseModel | activeModel instanceof RectangleModel) {
                        getCurrentController().setLockAspect(lockAspect);
                    }
                    setEnd(point);
                    getCurrentController().setEndCoordinates(end[0], end[1]);
                }

                else {
                    if (moving) {
                        getCurrentController().setMoveEnd((int) point.getX(), (int) point.getY());
                        getCurrentController().move();
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

        // add instance to existing models
        models.add(activeModel);

        // add controller
        controllers.add(controller);

        // add to draw panel
        drawPanel.addModel(activeModel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {

                        // If we draw a 2D shape, apply a fill colour
                        if (activeModel instanceof ShapeModel2D) {
                            // ((ShapeModel2D) activeModel).setFillColour(fillColourPicker.getColour());
                            ((Shape2DController) getCurrentController()).setFillColour(fillColourPicker.getColour());
                        }

                        // Set the model colour to the current state of the colour picker
                        // activeModel.setLineColour(lineColourPicker.getColour());
                        getCurrentController().setLineColour(lineColourPicker.getColour());
                        getCurrentController().setStrokeSize(strokeStyler.getStrokeSize());

                        // Update the position of the shape.
                        drawPanel.updateLastShape();

                        // repaint
                        mainFrame.repaint();
                    }
                });
    }

    /** Marks one button in the toolbox as active (bold & black) and all others
     * as inactive (plain & dark gray).
     * @param button The button to be activated. */
    public void activateButton(JButton button){

        moveMode = button.getText().equals("Move");

        for (JButton b : allButtons) {
            // Set button layout to active for the specified button
            if (b.equals(button)) {
                Font bold = b.getFont().deriveFont(Font.BOLD);
                b.setFont(bold);
                b.setForeground(Color.BLACK);
            }
            // Set button layout to inactive for all other buttons
            else {
                Font plain = b.getFont().deriveFont(Font.PLAIN);
                b.setFont(plain);
                b.setForeground(Color.DARK_GRAY);
            }
        }
    }

    public void setStart(Point p) {
        this.start[0] = (int) p.getX();
        this.start[1] = (int) p.getY();
    }

    public void setEnd(Point p) {
        this.end[0] = (int) p.getX();
        this.end[1] = (int) p.getY();
    }

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