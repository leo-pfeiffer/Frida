package view;

import controller.IShapeController;
import controller.LineController;
import model.IShapeModel;
import model.LineModel;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

public class FridaView implements Observer, ActionListener {

    /** Contains all active models. */
    private ArrayList<IShapeModel> models = new ArrayList<IShapeModel>();

    /** Contains all active controllers. */
    private ArrayList<IShapeController> controllers = new ArrayList<IShapeController>();

    /** The currently active model. */
    private IShapeModel activeModel;

    private static final int DEFAULT_FRAME_WIDTH = 1200;
    private static final int DEFAULT_FRAME_HEIGHT = 800;

    private static final int DRAW_PANEL_WIDTH = 1200;
    private static final int DRAW_PANEL_HEIGHT = 600;

    private static final Color DRAW_BACKGROUND_COLOUR = Color.WHITE;

    // View elements
    private JFrame mainFrame;
    private DrawPanel drawPanel;  // todo add drawPanel or whatever

    private JMenuBar menu;
    private JToolBar toolbox;

    private JButton undoButton;  // cmd+shift+z
    private JButton redoButton;  // cmd+z
    private JButton moveButton;
    private JButton lineButton;
    private JButton rectangleButton;
    private JButton parallelogramButton;
    private JButton triangleButton;
    private JButton hexagonButton;
    private JButton ellipseButton;
    private JButton starButton;
    private JButton clearButton;
    private ColourPicker colourPicker;

    /** A list containing all buttons. */
    private ArrayList<JButton> allButtons = new ArrayList<JButton>();

    @SuppressWarnings("deprecation")
    public FridaView() {

        // Initially, we use the line model
        activeModel = new LineModel();

        ((Observable) activeModel).addObserver(this);
        models.add(activeModel);
        controllers.add(new LineController((LineModel) activeModel));

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

        this.drawPanel.setBackground(DRAW_BACKGROUND_COLOUR);
        // this.drawPanel.setOpaque(true);

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
                getCurrentController().setEndCoordinates((int) point.getX(), (int) point.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {

                // start a line
                Point point = e.getPoint();
                getCurrentController().setStartCoordinates((int) point.getX(), (int) point.getY());
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

        load.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // todo call appropriate method in model
                JOptionPane.showMessageDialog(mainFrame, "Load not linked to model!");
            }
        });

        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // todo call appropriate method in model
                JOptionPane.showMessageDialog(mainFrame, "Save not linked to model!");
            }
        });

        mainFrame.setJMenuBar(menu);
    }

    /** Set up all components of the toolbox, add the ActionListeners and
     * add them to the toolbox. */
    public void setupToolbox() {

        // Create the components
        colourPicker = new ColourPicker("Colour");
        allButtons.add(colourPicker);

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
     * @param button The button the ActionListener is added to. */
    public void addActionListenerToButton(JButton button) {
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                // Activate the button and deactivate others
                activateButton(button);

                if (button.getText().equals("Line")) {
                    // Create new model instance
                    activeModel = new LineModel();

                    // Add observer to new instance
                    ((Observable) activeModel).addObserver(getOuterThis());

                    // add instance to existing models
                    models.add(activeModel);

                    // add controller
                    controllers.add(new LineController((LineModel) activeModel));

                    System.out.println("activeModel is LineModel");
                }
            }
        });
    }

    /** Get the class itself in order to use it in anonymous inner classes.
     * @return the reference to the current instance. */
    public FridaView getOuterThis() {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lineButton) {
            // delete the last element of models and controllers?
            // controller.controlClear();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        int[] start = getCurrentModel().getStartCoordinates();
                        int[] end = getCurrentModel().getEndCoordinates();

                        System.out.println("Start " + start[0] + " End: " + end[0]);

                        if (activeModel instanceof LineModel) {
                            drawPanel.setArguments(start[0], start[1], end[0], end[1]);
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
            if (b.equals(button)) {
                Font bold = b.getFont().deriveFont(Font.BOLD);
                b.setFont(bold);
                b.setForeground(Color.BLACK);
            }
            else {
                Font plain = b.getFont().deriveFont(Font.PLAIN);
                b.setFont(plain);
                b.setForeground(Color.DARK_GRAY);
            }
        }
    }

    /** Get the last (i.e. current) element in the controllers list.
     * @return Current controller. */
    public IShapeController getCurrentController() {
        return controllers.get(controllers.size() - 1);
    }

    /** Get the last (i.e. current) element in models list.
     * @return Current model. */
    public IShapeModel getCurrentModel() {
        return models.get(models.size() - 1);
    }
}