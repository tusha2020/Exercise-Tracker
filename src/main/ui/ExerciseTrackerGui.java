package ui;

import model.Event;
import model.EventLog;
import model.Exercise;
import model.Routine;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//The window in which the Exercise tracker will be visible on.

public class ExerciseTrackerGui extends JFrame implements ActionListener {
    private JButton scheduleBtn;
    private static final String JSON_STORE = "./data/trackerGUI.json";
    private JButton routineBtn;
    private JButton loadBtn;
    private ArrayList<Routine> routines;
    private Exercise exercise;
    private Routine simpleRoutine;
    private Schedule schedule;
    private JButton saveBtn;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private Routine hardRoutine;
    private JLabel logo;
    private JPanel topTextPanel;

    //EFFECTS: creates the menu in which the exercise tracker will be displayed.
    public ExerciseTrackerGui() {
        super("Exercise Tracker");

        createClosingOperation();

        setPreferredSize(new Dimension(400, 400));
        setLayout(new GridLayout(0, 1));
        schedule = new Schedule();

        initializeTopPanel();

        routines = new ArrayList<>();

        createAndAddRoutines();


        JPanel btnPanel = new JPanel();
        add(btnPanel);
        btnPanel.setLayout(new GridLayout(0, 1));
        btnPanel.setPreferredSize(new Dimension(100, 200));

        initializeBtns();

        btnPanel.add(scheduleBtn);
        btnPanel.add(routineBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(loadBtn);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    //MODIFIES: this
    //EFFECTS: creates closing operation
    private void createClosingOperation() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEventLog();
                System.exit(0);
            }
        });
    }


    //EFFECTS: prints all events in event log
    public void printEventLog() {
        EventLog log = EventLog.getInstance();
        Iterator<Event> iterator = log.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            System.out.println(event.getDate() + ": " + event.getDescription());
        }
    }



    //MODIFIES: this
    //EFFECTS: instantiates all buttons and sets up its event response.
    private void initializeBtns() {
        scheduleBtn = new JButton("View Schedule");
        routineBtn = new JButton("View Routines");
        loadBtn = new JButton("load saved file");
        saveBtn = new JButton("save changes");
        scheduleBtn.setActionCommand("schedule");
        routineBtn.setActionCommand("routines");
        loadBtn.setActionCommand("load");
        saveBtn.setActionCommand("save");
        scheduleBtn.addActionListener(this);
        routineBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        saveBtn.addActionListener(this);
    }


    //MODIFIES: this
    //EFFECTS: instantiates routines and adds exercises to them, and adds them to routines list.
    private void createAndAddRoutines() {
        simpleRoutine = new Routine("simple");
        hardRoutine = new Routine("hard");
        simpleRoutine.addExercise(new Exercise("run", 30));
        simpleRoutine.addExercise(new Exercise("stretch", 30));
        hardRoutine.addExercise(new Exercise("run", 60));
        hardRoutine.addExercise(new Exercise("jump", 60));
        hardRoutine.addExercise(new Exercise("jog", 30));

        exercise = new Exercise("squats", 30);
        simpleRoutine.addExercise(exercise);
        routines.add(simpleRoutine);
        routines.add(hardRoutine);
    }


    //MODIFIES: this
    //EFFECTS: adds a new panel with text and an image
    private void initializeTopPanel() {
        topTextPanel = new JPanel();
        add(topTextPanel, BorderLayout.NORTH);
        topTextPanel.setLayout(new BorderLayout());
        JLabel welcomeMenu = new JLabel("Welcome to your exercise tracker");
        JLabel whatToDo = new JLabel("please choose an action");
        topTextPanel.add(welcomeMenu, BorderLayout.CENTER);
        topTextPanel.add(whatToDo, BorderLayout.SOUTH);
        addLogoImage();
    }



    //MODIFIES: this
    //EFFECTS: adds logo image to this frame
    private void addLogoImage() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./data/ExerciseTracker.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(100, 100,
                Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        logo = new JLabel(imageIcon);
        topTextPanel.add(logo, BorderLayout.NORTH);
        pack();
    }


    @Override
    //MODIFIES: this
    //EFFECTS: performs action based on event given
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "schedule") {
            new ScheduleFrame(routines, schedule);
        } else if (e.getActionCommand() == "routines") {
            new RoutineFrame(routines);
        } else if (e.getActionCommand() == "load") {
            reload();
        } else {
            save();
        }
    }


    //EFFECTS: saves the current application to a file.
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule, routines);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "filed saved");

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: file was not saved");
        }
    }


    //MODIFIES: this
    //EFFECTS: loads exercise tracker from a saved file
    private void reload() {
        try {
            schedule = jsonReader.readSchedule();
            routines = jsonReader.readRoutines();
            JOptionPane.showMessageDialog(this, "loaded successfully");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: unable to load");
        }
    }


    //EFFECTS: runs the Exercise tracker.
    public static void main(String[] args) {
        new ExerciseTrackerGui();
    }

}
