package ui;

import model.Exercise;
import model.Routine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


//The window in which the routines will be visible on.
public class RoutineFrame extends JFrame {
    JLabel topTitle;
    private ArrayList<Routine> routines;

    //EFFECTS: constructs the frame which the schedule will be shown in.
    public RoutineFrame(ArrayList<Routine> routines) {
        super("Routines");
        this.routines = routines;
        setLayout(new GridLayout(0, 1));
        topTitle = new JLabel("Routines");
        add(topTitle);
        placeRoutines();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    //MODIFIES: this
    //EFFECTS: adds text for each routine with its length of time and exercises in a list
    private void placeRoutines() {
        for (Routine routine : routines) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            add(panel);

            String name = routine.getName();
            double timeMinutes = routine.getTotalSeconds() / 60;
            JLabel top = new JLabel("\nRoutine: " + name + " , time length: " + timeMinutes + " minutes");
            panel.add(top, BorderLayout.NORTH);

            ArrayList<Exercise> exercises = routine.getExercises();
            ArrayList<String> exerciseWithInfo = new ArrayList<>();
            for (Exercise e : exercises) {
                String exercise = "\n- " + e.getName() + " for " + e.getSeconds() + " seconds";
                exerciseWithInfo.add(exercise);
            }
            JList exerciseList = new JList(exerciseWithInfo.toArray());
            panel.add(exerciseList, BorderLayout.CENTER);
        }
    }

}
