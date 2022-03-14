package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collection;
// a schedule which has 7 days of the week and includes scheduled exercise entries

public class Schedule implements Writable {
    private ArrayList<ExerciseEntry> weeklySchedule;
    private double totalExerciseSeconds;

    //EFFECTS: creates a schedule for 7 days.
    public Schedule() {
        weeklySchedule = new ArrayList<>();
        totalExerciseSeconds = 0;
        for (int i = 0; i < 7; i++) {
            weeklySchedule.add(i, null);
        }
    }

    //MODIFIES: this
    //EFFECTS: if day has no scheduled exercise, adds exercise entry to day on schedule, adds entry time to total
    // exercise seconds and return true, otherwise return false.
    public boolean addExerciseEntry(ExerciseEntry entry, String day) {
        int index = dayToIndexNum(day);
        if (dayOccupied(day)) {
            return false;
        } else {
            weeklySchedule.set(index, entry);
            Routine r = entry.getRoutine();
            totalExerciseSeconds = totalExerciseSeconds + r.getTotalSeconds();
            EventLog.getInstance().logEvent(new Event("exercise entry added to " + day));
            return true;
        }
    }

    //REQUIRES: the index of given day is occupied.
    //MODIFIES: this
    //EFFECTS: removes the exercise in the given day and removes the entry's routine time from total exercise seconds of
    // week.
    public void removeExerciseEntry(String day) {
        int index = dayToIndexNum(day);
        ExerciseEntry entry = weeklySchedule.get(index);
        weeklySchedule.set(index, null);
        Routine r = entry.getRoutine();
        totalExerciseSeconds = totalExerciseSeconds - r.getTotalSeconds();
        EventLog.getInstance().logEvent(new Event("exercise entry removed from " + day));
    }


    //REQUIRES: the given day's index to have an exercise entry already added.
    //EFFECTS: returns which exercise entry corresponds to given day.
    public ExerciseEntry thisDaysExercise(String day) {
        int index = dayToIndexNum(day);
        return weeklySchedule.get(index);
    }

    //EFFECTS: returns the index number on schedule which corresponds to day given.
    public int dayToIndexNum(String day) {
        if (day.equalsIgnoreCase("MONDAY")) {
            return 0;
        } else if (day.equalsIgnoreCase("TUESDAY")) {
            return 1;
        } else if (day.equalsIgnoreCase("WEDNESDAY")) {
            return 2;
        } else if (day.equalsIgnoreCase("THURSDAY")) {
            return 3;
        } else if (day.equalsIgnoreCase("FRIDAY")) {
            return 4;
        } else if (day.equalsIgnoreCase("SATURDAY")) {
            return 5;
        } else {
            return 6;
        }
    }

    //REQUIRES: 0<= index <= 6
    //EFFECTS: returns day corresponding to given index number
    public String indexToDay(int index) {
        if (index == 0) {
            return "monday";
        } else if (index == 1) {
            return "tuesday";
        } else if (index == 2) {
            return "wednesday";
        } else if (index == 3) {
            return "thursday";
        } else if (index == 4) {
            return "friday";
        } else if (index == 5) {
            return "saturday";
        } else {
            return "sunday";
        }
    }

    //EFFECTS: returns the number of days on schedule
    public int scheduleDayNum() {
        return weeklySchedule.size();
    }

    //REQUIRES: given day is a day of the week.
    //EFFECTS: return true if given day has any scheduled exercise.
    public boolean dayOccupied(String day) {
        int index = dayToIndexNum(day);
        if (null == weeklySchedule.get(index)) {
            return false;
        } else {
            return true;
        }
    }

    //getter
    public double getTotalExerciseSeconds() {
        return totalExerciseSeconds;
    }



    //getter
    public ArrayList<ExerciseEntry> getWeeklySchedule() {
        return weeklySchedule;
    }


    @Override
    //EFFECTS: returns a JSON representation of this
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
       // jsonObject.put("totalExerciseSeconds", totalExerciseSeconds);
        jsonObject.put("Entries", entriesToJson());
        return jsonObject;
    }


    //EFFECTS: returns exercise entries of this schedule as a JSON array
    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ExerciseEntry e : weeklySchedule) {
            if (null == e) {
                JSONObject json = new JSONObject();
                json.put("time", "");
                json.put("place", "");
                json.put("routine","");
                jsonArray.put(json);
            } else {
                jsonArray.put(e.toJson());
            }
        }
        return jsonArray;
    }


}
