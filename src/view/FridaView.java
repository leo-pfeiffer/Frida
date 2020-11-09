package view;

import controller.IShapeController;
import model.IShapeModel;
import model.LineModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class FridaView implements Observer, ActionListener {

    private LineModel model;
    private IShapeController controller;

    private static final int DEFAULT_FRAME_WIDTH = 1200;
    private static final int DEFAULT_FRAME_HEIGHT = 800;

    private static final int DRAW_PANEL_WIDTH = 1200;
    private static final int DRAW_PANEL_HEIGHT = 600;

    private static final Color DRAW_BACKGROUND_COLOUR = Color.WHITE;

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

    public FridaView(LineModel model, IShapeController controller) {
        this.model = model;
        this.controller = controller;

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

        //
        ((Observable) model).addObserver(this);

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

        // todo check if this is needed
        // this.drawPanel.setMinimumSize(new Dimension(DRAW_PANEL_WIDTH, DRAW_PANEL_HEIGHT));
        // this.drawPanel.setMaximumSize(new Dimension(DRAW_PANEL_WIDTH, DRAW_PANEL_HEIGHT));

        // add a MouseListener that listens for clicks and releases
        this.drawPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                // System.out.println("Mouse released " + e.getX() + " " + e.getY());

                // end a line
                Point point = e.getPoint();
                controller.controlSetEndCoordinates((int) point.getX(), (int) point.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // System.out.println("Mouse pressed " + e.getX() + " " + e.getY());

                // start a line
                Point point = e.getPoint();
                controller.controlSetStartCoordinates((int) point.getX(), (int) point.getY());
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
                // System.out.println("Mouse dragged " + e.getX() + " " + e.getY());
                // todo
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });

        // add the drawPanel to the center of the main frame
        mainFrame.add(drawPanel, BorderLayout.CENTER);
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

    public void setupToolbox() {

        // Create the colour picker
        colourPicker = new ColourPicker("Colour");

        undoButton = new JButton("Undo");

        undoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        undoButton = new JButton("Undo");

        undoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        redoButton = new JButton("Redo");

        redoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        moveButton = new JButton("Move");

        moveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        lineButton = new JButton("Line");

        lineButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        // Setup rectangle button
        rectangleButton = new JButton("Rectangle");

        rectangleButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        parallelogramButton = new JButton("Parallelogram");

        parallelogramButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        triangleButton = new JButton("Triangle");

        triangleButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        hexagonButton = new JButton("Hexagon");

        hexagonButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        ellipseButton = new JButton("Ellipse");

        ellipseButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        starButton = new JButton("Star");

        starButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        clearButton = new JButton("Clear");

        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Button not linked to model!");
            }
        });

        // add buttons to the toolbox
        toolbox.add(colourPicker);
        toolbox.add(undoButton);
        toolbox.add(redoButton);
        toolbox.add(moveButton);
        toolbox.add(lineButton);
        toolbox.add(rectangleButton);
        toolbox.add(parallelogramButton);
        toolbox.add(triangleButton);
        toolbox.add(hexagonButton);
        toolbox.add(ellipseButton);
        toolbox.add(starButton);
        toolbox.add(clearButton);

        // add toolbox to the top of the main frame
        mainFrame.add(toolbox, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lineButton) {
            // controller.controlClear();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        int[] start = model.getStartCoordinates();
                        int[] end = model.getEndCoordinates();

                        System.out.println("Start " + start[0] + " End: " + end[0]);

                        drawPanel.setArguments(start[0], start[1], end[0], end[1]);

                        mainFrame.repaint();
                    }
                });
    }
}