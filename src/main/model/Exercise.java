package model;
// an exercise with total time to finish it and whether it has been completed or not.

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

public class Exercise implements Writable {
    private String name;
    private int seconds;
    private boolean completion;

    public Exercise(String name, int seconds) {
        this.name = name;
        this.seconds = seconds;
        completion = false;
    }

    //MODIFIES: this
    //EFFECTS: if incomplete marks exercise as completed.
    public void completeExercise() {
        completion = true;
    }

    //getter
    public boolean getCompletion() {
        return completion;
    }

    //getter
    public int getSeconds() {
        return seconds;
    }

    //getter
    public String getName() {
        return name;
    }


    @Override
    //EFFECTS: creates JSON representation of this object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("exerciseName", name);
        json.put("seconds", seconds);
        json.put("completion", completion);

        return json;
    }


}
