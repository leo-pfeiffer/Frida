package view;

import controller.IShapeController;
import controller.LineController;
import controller.Shape2DController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    /** Main menu for saving, loading etc. */
    private JMenuBar menu;
    /** Toolbox to switch shapes etc. */
    private JToolBar toolbox;

    // Buttons
    /** Button to undo last action. */
    private JButton undoButton;
    /** Button to redo last action. */
    private JButton redoButton;
    /** Button to activate moving a shape. */
    private JButton moveButton;
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
    /** Button to activate clear mode. */
    private JButton clearButton;
    /** Button to pick a new line colour. */
    private ColourPicker lineColourPicker;
    /** Button to pick a new fill colour */
    private ColourPicker fillColourPicker;

    /** A list containing all buttons. */
    private ArrayList<JButton> allButtons = new ArrayList<>();

    /** The current state of the programme, i.e. draw, undo, redo, move, ... */
    // todo find a smarter way to solve this
    private String state;

    /** Start coordinates. */
    private int[] start = new int[2];
    /** Start coordinates. */
    private int[] end = new int[2];

    /** Construct a new object by setting up the components. */
    public FridaView() {

        // Initially, we use the line model
        activeModel = new LineModel();

        // add observer, add model to models, add controller to controllers
        setupNewModel(new LineController((LineModel) activeModel));

        // Create the main frame for the view
        mainFrame = new JFrame("Frida");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        mainFrame.setVisible(true);

        // Create the control and draw panels
        drawPanel = new DrawPanel();

        // Create the menu and toolbox
        menu = new JMenuBar();
        toolbox = new JToolBar();

        // Setup the components
        this.setupComponents();

        // Paint and pack the main frame and its components
        mainFrame.paintAll(mainFrame.getGraphics());
        mainFrame.pack();
    }

    /** Method to setup the different components of the main frame, i.e. toolbox and menu,
     * and add them to the main frame. */
    public void setupComponents() {
        this.setupPanel();
        this.setupMenu();
        this.setupToolbox();
        // mainFrame.add(outputPane, BorderLayout.CENTER);
    }

    /** Setup the draw panel */
    public void setupPanel() {

        this.drawPanel.setForeground(DRAW_BACKGROUND_COLOUR);
        drawPanel.setOpaque(true);

        // Set the preferred size of the draw panel.
        this.drawPanel.setPreferredSize(new Dimension(DRAW_PANEL_WIDTH, DRAW_PANEL_HEIGHT));

        // add a MouseListener that listens for clicks and releases
        this.drawPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                // end a line
                Point point = e.getPoint();
                setEnd(point);
                createNewModel();
                getCurrentController().setStartCoordinates(start[0], start[1]);
                getCurrentController().setEndCoordinates(end[0], end[1]);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // start a line
                Point point = e.getPoint();
                setStart(point);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        // Add a motion listener to listen for mouse drags
        this.drawPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // todo
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });

        // add the drawPanel to the center of the main frame
        mainFrame.add(drawPanel, BorderLayout.SOUTH);
    }

    public void setupMenu() {
        // Fill menu bar
        JMenu file = new JMenu ("File");
        JMenuItem load = new JMenuItem ("Load");
        JMenuItem save = new JMenuItem ("Save");
        JMenuItem help = new JMenuItem ("Help");
        file.add (load);
        file.add (save);
        file.add (help);
        menu.add (file);

        load.addActionListener(e -> {
            // todo call appropriate method in model
            JOptionPane.showMessageDialog(mainFrame, "Load not linked to model!");
        });

        save.addActionListener(e -> {
            // todo call appropriate method in model
            JOptionPane.showMessageDialog(mainFrame, "Save not linked to model!");
        });

        mainFrame.setJMenuBar(menu);
    }

    /** Set up all components of the toolbox, add the ActionListeners and
     * add them to the toolbox. */
    public void setupToolbox() {

        // Create the components
        lineColourPicker = new ColourPicker("Line Colour", Color.BLACK);
        allButtons.add(lineColourPicker);

        fillColourPicker = new ColourPicker("Fill Colour", drawPanel.getBackground());
        allButtons.add(fillColourPicker);

        undoButton = new JButton("Undo");
        allButtons.add(undoButton);

        redoButton = new JButton("Redo");
        allButtons.add(redoButton);

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

        clearButton = new JButton("Clear");
        allButtons.add(clearButton);

        for (JButton b : allButtons) {
            // add ActionListeners
            addActionListenerToButton(b);

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
                case "Undo" -> {
                    drawPanel.undo();
                    mainFrame.repaint();
                }
                case "Redo" -> {
                    drawPanel.redo();
                    mainFrame.repaint();
                }
                case "Move" -> {
                    activateButton(b);
                    state = "move";
                }
                case "Line" -> {
                    activateButton(b);
                    // set active model but don't set up the model yet
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
                }
                case "Ellipse" -> {
                    activateButton(b);
                    System.out.println("Ellipse...");
                }
                case "Star" -> {
                    activateButton(b);
                    activeModel = new StarModel();
                }
                case "Clear" -> {
                    drawPanel.clearAll();
                    mainFrame.repaint();
                }
                case "Line Colour" -> System.out.println("Line Colour...");
                case "Fill Colour" -> System.out.println("Fill Colour...");
                default -> System.out.println("Unexpected button: " + b.getText());
            }
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

        // Set state to draw
        state = "draw";
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        switch (state) {
                            case "draw":
                                int x = 1;
                                // If we draw a 2D shape, apply a fill colour
                                if (activeModel instanceof ShapeModel2D) {
                                    ((ShapeModel2D) activeModel).setFillColour(fillColourPicker.getColour());
                                }
                                // Set the model colour to the current state of the colour picker
                                activeModel.setLineColour(lineColourPicker.getColour());
                                drawPanel.addModel(activeModel);
                                break;

                            case "move":
                                break;

                            default:
                                System.err.println("Unexpected case: " + state);
                        }

                        mainFrame.repaint();
                    }
                });
    }

    /** Marks one button in the toolbox as active (bold & black) and all others
     * as inactive (plain & dark gray).
     * @param button The button to be activated. */
    public void activateButton(JButton button){
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
            setupNewModel(new LineController((LineModel) activeModel));
        }

        // New rectangle
        else if (activeModel instanceof RectangleModel) {
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

    }

    /** Get the last (i.e. current) element in the controllers list.
     * @return Current controller. */
    public IShapeController getCurrentController() {
        return controllers.get(controllers.size() - 1);
    }
}