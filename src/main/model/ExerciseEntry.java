package model;

import org.json.JSONObject;
import persistence.Writable;

//an exercise entry which describes time, place of the exercise and the exercise routine to be performed.
public class ExerciseEntry implements Writable {
    private String time;  //time in seconds
    private String place;
    private Routine routine;

    //EFFECTS: creates new exercise entry which describes the routine, place and the time of exercise.
    public ExerciseEntry(String place, Routine routine, String time) {
        this.time = time;
        this.place = place;
        this.routine = routine;
    }

    //REQUIRES: newTime>=0
    //MODIFIES: this
    //EFFECTS: replaces current time to the given new time.
    public void changeTime(String newTime) {
        time = newTime;
    }

    //MODIFIES: this
    //EFFECTS: replaces current place to the given new place.
    public void changePlace(String newPlace) {
        place = newPlace;
    }

    //MODIFIES: this
    //EFFECTS: replaces current routine to the given new routine.
    public void changeRoutine(Routine newRoutine) {
        routine = newRoutine;
    }


    //getter
    public String getTime() {
        return time;
    }


    //getter
    public String getPlace() {
        return place;
    }


    //getter
    public Routine getRoutine() {
        return routine;
    }


    @Override
    //EFFECTS: returns JSON representation of this object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("time", time);
        json.put("place", place);
        json.put("routine", routine.toJson());

        return json;
    }
}
