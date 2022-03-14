package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//exercise routine which includes different exercises to do and has a total time to finish it.
public class Routine implements Writable {
    private String name;
    private ArrayList<Exercise> exercises;
    private double totalSeconds;

    //EFFECTS: creates a new routine
    public Routine(String name) {
        this.name = name;
        exercises = new ArrayList();
        totalSeconds = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds exercise to the top of routine and adds its time to total seconds of routine.
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        totalSeconds = totalSeconds + exercise.getSeconds();
    }

    //REQUIRES: exercise to already be added in routine.
    //MODIFIES: this
    //EFFECTS: removes exercise from routine and removes its total seconds from total seconds of routine.
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
        totalSeconds = totalSeconds - exercise.getSeconds();
    }


    //REQUIRES: routine to have exercises in it already.
    //EFFECTS: returns first exercise on routine.
    public Exercise getExercise() {
        int lastAddedIndex = exercises.size() - 1;
        return exercises.get(lastAddedIndex);
    }


    //getter
    public double getTotalSeconds() {
        return totalSeconds;
    }


    //EFFECTS: returns number of the exercises in the list
    public int routineSize() {
        return exercises.size();
    }



    //getter
    public String getName() {
        return name;
    }

    //getter
    public ArrayList<Exercise> getExercises() {
        return exercises;
    }


    @Override
    //EFFECTS: returns a JSON representation of this object.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("totalSeconds", totalSeconds);
        json.put("exercises", exercisesToJson());

        return json;
    }


    //EFFECTS: returns a JSON array to represent exercises of this routine
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : exercises) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
