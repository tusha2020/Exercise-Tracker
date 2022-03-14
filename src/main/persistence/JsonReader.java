package persistence;

import model.Exercise;
import model.ExerciseEntry;
import model.Routine;
import model.Schedule;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// This [class] references code from JsonSerializationDemo
// Link: [https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git]

// A reader which reads the schedule and routine list from JSON data stored in a file

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads schedule from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Schedule readSchedule() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSchedule(jsonObject);
    }


    // EFFECTS: reads list of routines from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Routine> readRoutines() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRoutines(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses schedule from JSON object and returns it
    private Schedule parseSchedule(JSONObject jsonObject) {
        Schedule schedule = new Schedule();
        addExerciseEntries(schedule, jsonObject);
        return schedule;
    }

    // EFFECTS: parses routine list from JSON object and returns it
    private ArrayList<Routine> parseRoutines(JSONObject jsonObject) {
        ArrayList<Routine> routines = new ArrayList<>();
        addRoutines(routines, jsonObject);

        return routines;
    }


    //MODIFIES: routines
    //EFFECTS: parses routines from JSON objects and adds them to routines list
    private void addRoutines(ArrayList<Routine> routines, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("routines");
        for (Object json : jsonArray) {
            JSONObject nextRoutine = (JSONObject) json;
            addRoutine(routines, nextRoutine);
        }
    }


    //MODIFIES: routines
    //EFFECTS: parses routine from JSON object and adds it to routines
    private void addRoutine(ArrayList<Routine> routines, JSONObject nextRoutine) {
        String name = nextRoutine.getString("name");
        Double totalSeconds = nextRoutine.getDouble("totalSeconds");
        JSONArray jsonArrayExercises = nextRoutine.getJSONArray("exercises");

        Routine routine = new Routine(name);
        for (Object json : jsonArrayExercises) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(routine, nextExercise);
        }

        routines.add(routine);
    }



    //MODIFIES: routine
    //EFFECTS: parses exercise from JSON object and adds it to given routine
    private void addExercise(Routine routine, JSONObject nextExercise) {
        String name =  nextExercise.getString("exerciseName");
        Integer seconds = nextExercise.getInt("seconds");
        Exercise exercise = new Exercise(name, seconds);
        routine.addExercise(exercise);
    }


    // MODIFIES: sc
    // EFFECTS: parses Exercise entries from JSON object and adds them to schedule
    private void addExerciseEntries(Schedule sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Entries");
        int index = 0;
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addExerciseEntry(sc, nextEntry, index);
            index = index + 1;
        }
    }


    //MODIFIES: sc
    //EFFECTS: parses exercise entry from JSON object and adds it to schedule
    private void addExerciseEntry(Schedule sc, JSONObject nextEntry, int index) {
        String time = nextEntry.getString("time");
        String place = nextEntry.getString("place");

        if (!time.equalsIgnoreCase("")) {
            JSONObject routineJson = nextEntry.getJSONObject("routine");
            String routineName = routineJson.getString("name");
            JSONArray exercises = routineJson.getJSONArray("exercises");
            Routine routine = new Routine(routineName);

            for (Object json : exercises) {
                JSONObject nextExercise = (JSONObject) json;
                addExercise(routine, nextExercise);
            }

            ExerciseEntry entry = new ExerciseEntry(place, routine, time);
            sc.addExerciseEntry(entry, sc.indexToDay(index));
        }
    }

}
