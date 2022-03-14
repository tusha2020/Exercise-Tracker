package ui;

import model.ExerciseEntry;
import model.Routine;
import model.Schedule;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//The window in which the Exercise weekly schedule will be visible on.
public class ScheduleFrame extends JFrame implements ActionListener {
    private Object[] routineNames;
    private ArrayList<Routine> routines;
    private String[] timeStrings = {"9:00 am",
            "9:30 am",
            "10:00 am",
            "10:30 am",
            "11:00 am",
            "11:30 am",
            "12:00 pm",
            "12:30 pm",
            "1:00 pm",
            "1:30 pm",
            "2:00 pm",
            "2:30 pm",
            "3:00 pm",
            "3:30 pm",
            "4:00 pm",
            "4:30 pm",
            "5:00 pm",
            "5:30 pm",
            "6:00 pm",
            "6:30 pm",
            "7:00 pm",
            "7:30 pm",
            "8:00 pm"
    };
    private Routine routine;
    private String time;
    private String place;
    private String dayChosen;
    private JTextField placeField = new JTextField(5);
    private Schedule schedule;
    private JLabel topTitle;
    private ScheduleTablePanel table;
    private JTextField field = new JTextField(5);
    private JTextField removedField = new JTextField(5);
    private JPanel panel;
    private JButton addBtn;
    private JButton removeBtn;
    private JLabel placeLabel = new JLabel("place");
    private JLabel timeLabel = new JLabel("time");
    private JLabel routineLabel = new JLabel("routine");
    private JLabel choosePrompt = new JLabel("please choose the the routine, time and place:");

    //EFFECTS: constructs the frame which the schedule will be shown in.
    public ScheduleFrame(ArrayList<Routine> routines, Schedule schedule) {
        super("Schedule");
        this.schedule = schedule;
        this.routines = routines;
        table = new ScheduleTablePanel(schedule);
        setLayout(new BorderLayout());
        topTitle = new JLabel("Weekly Schedule");
        add(topTitle, BorderLayout.NORTH);

        add(table, BorderLayout.CENTER);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());
        addBtn = new JButton("add an exercise entry");
        addPanel.add(addBtn, BorderLayout.NORTH);
        addPanel.setPreferredSize(new Dimension(200, 100));
        add(addPanel, BorderLayout.EAST);

        addBtn.setActionCommand("add entry");
        addBtn.addActionListener(this);
        removeBtn = new JButton("remove an exercise entry");
        removeBtn.setActionCommand("remove entry");
        removeBtn.addActionListener(this);
        addPanel.add(removeBtn, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    //MODIFIES: this
    //EFFECTS: responds to action event.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "add entry") {
            addPanel(field, "add day");
        } else if (e.getActionCommand() == "remove entry") {
            addPanel(removedField, "removed day");
        } else if (e.getActionCommand() == "add day") {
            String dayToAddTo = field.getText();
            addEntryToDay(dayToAddTo);
        } else if (e.getActionCommand() == "removed day") {
            String dayRemovedFrom = removedField.getText();
            removeEntryFromDay(dayRemovedFrom);
        } else if (e.getActionCommand() == "routines") {
            JComboBox cb = (JComboBox) e.getSource();
            String routineName = (String) cb.getSelectedItem();
            routine = findRoutine(routineName);
        } else if (e.getActionCommand() == "time") {
            JComboBox cb = (JComboBox) e.getSource();
            time = (String) cb.getSelectedItem();
        } else {
            place = placeField.getText();
            createEntryAddToSchedule();
            updateScheduleInterface();
        }

    }


    //MODIFIES: this
    //EFFECTS: creates a new Exercise Entry with this place, routine and time, and adds it to this schedule.
    private void createEntryAddToSchedule() {
        ExerciseEntry entry = new ExerciseEntry(place, routine, time);
        schedule.addExerciseEntry(entry, dayChosen);
    }


    //EFFECTS: returns the Routine with the given name from routines.
    private Routine findRoutine(String stringRoutine) {
        for (Routine routine : routines) {
            String name = routine.getName();
            if (stringRoutine.equalsIgnoreCase(name)) {
                return routine;
            }
        }
        return null;
    }


    //MODIFIES: this
    //EFFECTS: constructs and adds Jpanel with a text label prompt to remove/add and a text field for inputting the day
    public void addPanel(JTextField field, String actionCommand) {
        if (panel != null) {
            remove(panel);
        }
        field.addActionListener(this);
        field.setActionCommand(actionCommand);
        String askMessage;
        if (actionCommand.equalsIgnoreCase("add day")) {
            askMessage = "add an entry to";
        } else {
            askMessage = "remove an entry from";
        }
        JLabel askUser = new JLabel("please input the day you would like to " + askMessage);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        panel.add(askUser, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        pack();

    }


    //MODIFIES: this
    //EFFECTS: removes the exercise entry from this schedule, and displays new schedule panel, and new panel
    // with text label to confirm removal.
    private void removeEntryFromDay(String dayRemovedFrom) {
        schedule.removeExerciseEntry(dayRemovedFrom);
        updateScheduleInterface();
        remove(panel);
        panel = new JPanel();
        JLabel removedMessage = new JLabel("entry removed from " + dayRemovedFrom);
        panel.add(removedMessage);
        add(panel, BorderLayout.SOUTH);
        pack();
    }


    //MODIFIES: this
    //EFFECTS: replace Jpanel with panel to collect user input, and creates and adds new entry to schedule.
    private void addEntryToDay(String dayToAddTo) {
        dayChosen = dayToAddTo;
        remove(panel);
        pack();
        initAndAddComboBoxesAndFieldPanel();
    }


    //MODIFIES: this
    //EFFECTS: constructs combination boxes for time, routine and creates a text field for place, adds them to
    // a panel at the south of this Jframe.
    private void initAndAddComboBoxesAndFieldPanel() {
        createRoutineNameList();
        JComboBox routineNameList = new JComboBox(routineNames);
        routineNameList.setSelectedIndex(0);
        routineNameList.setActionCommand("routines");
        routineNameList.addActionListener(this);

        JComboBox timesList = new JComboBox(timeStrings);
        timesList.setSelectedIndex(0);
        timesList.setActionCommand("time");
        timesList.addActionListener(this);

        placeField.setActionCommand("place");
        placeField.addActionListener(this);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(choosePrompt);
        panel.add(routineLabel);
        panel.add(routineNameList);
        panel.add(timeLabel);
        panel.add(timesList);
        panel.add(placeLabel);
        panel.add(placeField);

        add(panel, BorderLayout.SOUTH);
    }


    //MODIFIES: this
    //EFFECTS: creates a list of the names of the elements in routines.
    private void createRoutineNameList() {
        ArrayList<String> namesArray = new ArrayList<>();
        namesArray.add("");
        for (Routine routine : routines) {
            String name = routine.getName();
            namesArray.add(name);
        }
        routineNames = namesArray.toArray();
    }


    //MODIFIES: this
    //EFFECTS: replaces schedule table with a new table at same location on Jframe.
    public void updateScheduleInterface() {
        remove(table);
        table = new ScheduleTablePanel(schedule);
        add(table, BorderLayout.CENTER);
        pack();
    }
}
