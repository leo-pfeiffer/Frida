package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class FridaView implements Observer, ActionListener {

    private static int DEFAULT_FRAME_WIDTH = 600;
    private static int DEFAULT_FRAME_HEIGHT = 400;

    private JFrame mainFrame;
    private JPanel drawPanel;  // todo add drawPanel or whatever

    private JMenuBar menu;
    private JToolBar toolbox;
    private JButton colourButton;
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

    public FridaView() {
        // model
        // controller

        // Create the main frame for the view
        mainFrame = new JFrame("Frida");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        mainFrame.setVisible(true);

        // Create the control and draw panels
        // controlPanel = new JPanel();
        drawPanel = new JPanel();

        // Create the menu and toolbox
        menu = new JMenuBar();
        toolbox = new JToolBar();

        // Setup the components
        this.setupComponents();
    }

    /** Method to setup the different components of the main frame, i.e. toolbox and menu,
     * and add them to the main frame. */
    public void setupComponents() {
        this.setupMenu();
        this.setupToolbox();
        // mainFrame.add(outputPane, BorderLayout.CENTER);
    }

    public void setupMenu() {
        // Fill menu bar
        JMenu file = new JMenu ("File");
        JMenuItem load = new JMenuItem ("Load");
        JMenuItem save = new JMenuItem ("Save");
        file.add (load);
        file.add (save);
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
        /*
                private JButton RectangleButton;
                private JButton parallelogramButton;
                private JButton triangleButton;
                private JButton hexagonButton;
                private JButton ellipseButton;
                private JButton starButton;
                private JButton clearButton;*/
        colourButton = new JButton("Colour");  // todo change this to appropriate button

        colourButton.addActionListener(new ActionListener(){
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
        toolbox.add(colourButton);
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

        // add toolbox to the main frame
        mainFrame.add(toolbox, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}