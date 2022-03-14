package persistence;

import model.Routine;
import model.Schedule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

// This [class] references code from JsonSerializationDemo
// Link: [https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git]
//represents writer which writes a JSON representation of schedule and routines to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;


    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }


    // MODIFIES: this
    // EFFECTS: writes JSON representation of schedule and routines to file
    public void write(Schedule schedule, ArrayList<Routine> routines) {
        JSONObject json = schedule.toJson();
        json.put("routines", routinesToJson(routines));
        saveToFile(json.toString(TAB));
    }


    //MODIFIES: this
    //EFFECTS: creates a Json representation of the routines list.
    private JSONArray routinesToJson(ArrayList<Routine> routines) {
        JSONArray jsonArray = new JSONArray();
        for (Routine routine : routines) {
            jsonArray.put(routine.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }


    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
