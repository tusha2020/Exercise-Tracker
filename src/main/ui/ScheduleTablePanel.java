package ui;

import model.Schedule;
import javax.swing.*;
import java.awt.*;
// This [class] references code from SimpleTableDemo.java
// Link: [https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/SimpleTableDemoProject/src/components/SimpleTableDemo.java]

// a table representing the weekly schedule
public class ScheduleTablePanel extends JPanel {
    private MyTableModel tableModel;
    private JTable table;


    //EFFECTS: constructs a new panel with a table representing the schedule.
    public ScheduleTablePanel(Schedule schedule) {
        super(new BorderLayout());
        tableModel = new MyTableModel(schedule);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);
        double totalSeconds = schedule.getTotalExerciseSeconds();
        double totalMinutes = totalSeconds / 60;
        JLabel totalLabel = new JLabel("you have scheduled " + totalMinutes + " minutes of exercise this week");
        add(totalLabel, BorderLayout.SOUTH);
    }


    //Getter
    public MyTableModel getTableModel() {
        return tableModel;
    }
}
