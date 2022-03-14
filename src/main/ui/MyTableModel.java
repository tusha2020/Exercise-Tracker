package ui;

import model.ExerciseEntry;
import model.Schedule;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
// This [class] references code from SimpleTableDemo.java
// Link: [https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/SimpleTableDemoProject/src/components/SimpleTableDemo.java]

// A model of Jtable
public class MyTableModel extends AbstractTableModel {
    private Schedule schedule;

    private String[] columnNames = {"",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
    };

    private String[][] data = {
            {"9:00 am", "",
                    "", "", "", "", "", ""},
            {"9:30 am", "",
                    "", "", "", "", "", ""},
            {"10:00 am", "",
                    "", "", "", "", "", ""},
            {"10:30 am", "",
                    "", "", "", "", "", ""},
            {"11:00 am", "",
                    "", "", "", "", "", ""},
            {"11:30 am", "",
                    "", "", "", "", "", ""},
            {"12:00 pm", "",
                    "", "", "", "", "", ""},
            {"12:30 pm", "",
                    "", "", "", "", "", ""},
            {"1:00 pm", "",
                    "", "", "", "", "", ""},
            {"1:30 pm", "",
                    "", "", "", "", "", ""},
            {"2:00 pm", "",
                    "", "", "", "", "", ""},
            {"2:30 pm", "",
                    "", "", "", "", "", ""},
            {"3:00 pm", "",
                    "", "", "", "", "", ""},
            {"3:30 pm", "",
                    "", "", "", "", "", ""},
            {"4:00 pm", "",
                    "", "", "", "", "", ""},
            {"4:30 pm", "",
                    "", "", "", "", "", ""},
            {"5:00 pm", "",
                    "", "", "", "", "", ""},
            {"5:30 pm", "",
                    "", "", "", "", "", ""},
            {"6:00 pm", "",
                    "", "", "", "", "", ""},
            {"6:30 pm", "",
                    "", "", "", "", "", ""},
            {"7:00 pm", "",
                    "", "", "", "", "", ""},
            {"7:30 pm", "",
                    "", "", "", "", "", ""},
            {"8:00 pm", "",
                    "", "", "", "", "", ""}

    };


    //EFFECTS: constructs a table model with a schedule object, and modifies data based on the schedule.
    public MyTableModel(Schedule schedule) {
        this.schedule = schedule;
        modifyData();
    }


    //MODIFIES: this
    //EFFECTS: creates a string array to represent the schedule's exercise entries.
    public void modifyData() {
        ArrayList<ExerciseEntry> entries = schedule.getWeeklySchedule();
        int column = 1;
        for (ExerciseEntry e : entries) {
            if (e != null) {
                String time = e.getTime();
                String routine = e.getRoutine().getName();
                String place = e.getPlace();

                String entryText = "at " + place + " do " + routine + " routine";
                addEntryToThisRowColumn(time, column, entryText);
            }
            column++;
        }
    }


    //MODIFIES: this
    //EFFECTS: sets the entryText into the table at given time and column.
    private void addEntryToThisRowColumn(String time, int column, String entryText) {
        Integer row = timeToRowNum(time);
        data[row][column] = entryText;
    }



    //EFFECTS: returns an integer representing the row in which this time belongs to on data.
    private Integer timeToRowNum(String time) {
        Integer rowNum = 0;

        for (String[] row : data) {
            if (Arrays.asList(row).contains(time)) {
                return rowNum;
            }
            rowNum++;
        }
        return null;
    }



    //getter
    public int getColumnCount() {
        return columnNames.length;
    }


    //getter
    public int getRowCount() {
        return data.length;
    }


    //getter
    public String getColumnName(int col) {
        return columnNames[col];
    }


    //getter
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }
}
