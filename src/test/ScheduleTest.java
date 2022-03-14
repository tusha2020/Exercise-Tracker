import model.Exercise;
import model.ExerciseEntry;
import model.Routine;
import model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    private Schedule schedule;

    @BeforeEach
    void initialize() {
        schedule = new Schedule();
    }

    @Test
    void testConstructor() {
        assertEquals(0, schedule.getTotalExerciseSeconds());
        assertEquals(7, schedule.scheduleDayNum());
    }

    @Test
    void testAddExerciseEntryFreeDay() {
        Exercise e1 = new Exercise("squats", 30);
        Routine r1 = new Routine("r1");
        r1.addExercise(e1);
        ExerciseEntry entry = new ExerciseEntry("home", r1, "12:00");
        Boolean added = schedule.addExerciseEntry(entry, "monday");
       ExerciseEntry mondaysExercise =  schedule.thisDaysExercise("monday");

       assertEquals(entry, mondaysExercise);
       assertTrue(added);
       assertEquals(30, schedule.getTotalExerciseSeconds());
    }

    @Test
    void testAddExerciseEntryTwoOnDiffFreeDays() {
        Exercise e1 = new Exercise("squats", 30);
        Routine r1 = new Routine("r1");
        r1.addExercise(e1);
        ExerciseEntry entry = new ExerciseEntry("home", r1, "12:00");

        Exercise e2 = new Exercise("squats", 100);
        Routine r2 = new Routine("r1");
        r2.addExercise(e2);
        ExerciseEntry entry2 = new ExerciseEntry("home", r2, "11:00");

        Boolean added = schedule.addExerciseEntry(entry, "monday");
        Boolean added2 = schedule.addExerciseEntry(entry2, "tuesday");
        ExerciseEntry mondaysExercise =  schedule.thisDaysExercise("monday");
        ExerciseEntry tuesdaysExercise =  schedule.thisDaysExercise("tuesday");

        assertTrue(added);
        assertTrue(added2);
        assertEquals(entry, mondaysExercise);
        assertEquals(entry2, tuesdaysExercise);
        assertEquals(130, schedule.getTotalExerciseSeconds());
    }

    @Test
    void testAddExerciseEntryOccupiedDay() {
        Exercise e1 = new Exercise("squats", 60);
        Routine r1 = new Routine("r4");
        r1.addExercise(e1);
        ExerciseEntry entry1 = new ExerciseEntry("home", r1, "12:00");

        Routine r2 = new Routine("ro");
        ExerciseEntry entry2 = new ExerciseEntry("home", r2, "1:00");

        boolean added1 = schedule.addExerciseEntry(entry1, "monday");
        boolean added2 = schedule.addExerciseEntry(entry2, "monday");
        ExerciseEntry mondaysExercise =  schedule.thisDaysExercise("monday");

        assertEquals(60, schedule.getTotalExerciseSeconds());
        assertEquals(entry1, mondaysExercise);
        assertTrue(added1);
        assertFalse(added2);
    }

    @Test
    void testThisDaysExercise() {
        Routine r1 = new Routine("ro");
        ExerciseEntry entry1 = new ExerciseEntry("home", r1, "12:00");

        Routine r2 = new Routine("ru");
        ExerciseEntry entry2 = new ExerciseEntry("home", r2, "1:00");

        Boolean added1 = schedule.addExerciseEntry(entry1, "monday");
        Boolean added2 = schedule.addExerciseEntry(entry2, "tuesday");

       ExerciseEntry mondaysExercise = schedule.thisDaysExercise("monday");
       ExerciseEntry tuesdaysExercise = schedule.thisDaysExercise("tuesday");

       assertEquals(entry1, mondaysExercise);
       assertEquals(entry2, tuesdaysExercise);
    }

    @Test
    void testDayToIndexNum() {
        int monday = schedule.dayToIndexNum("MONDAY");
        int tuesday = schedule.dayToIndexNum("tuesday");
        int sunday = schedule.dayToIndexNum("sunday");
        int  wednesday = schedule.dayToIndexNum("wednesday");
        int thursday = schedule.dayToIndexNum("ThuRsdAy");
        int friday = schedule.dayToIndexNum("FRIDAY");
        int saturday = schedule.dayToIndexNum("Saturday");

        assertEquals(0, monday);
        assertEquals(1, tuesday);
        assertEquals(6, sunday);
        assertEquals(2, wednesday);
        assertEquals(3, thursday);
        assertEquals(4, friday);
        assertEquals(5, saturday);
    }


    @Test
    void testIndexToDay() {
        String monday = schedule.indexToDay(0);
        String tuesday = schedule.indexToDay(1);
        String  sunday = schedule.indexToDay(6);
        String  wednesday = schedule.indexToDay(2);
        String thursday = schedule.indexToDay(3);
        String friday = schedule.indexToDay(4);
        String saturday = schedule.indexToDay(5);

        assertEquals("monday", monday);
        assertEquals("tuesday", tuesday);
        assertEquals("sunday", sunday);
        assertEquals("wednesday", wednesday);
        assertEquals("thursday", thursday);
        assertEquals("friday", friday);
        assertEquals("saturday", saturday);
    }

    @Test
    void testScheduleDayNum() {
        Integer weekDaysNum = schedule.scheduleDayNum();
        assertEquals(7, weekDaysNum);
    }

    @Test
    void testRemoveExerciseEntry() {
        Exercise e1 = new Exercise("squats", 30);
        Routine r1 = new Routine("r");
        r1.addExercise(e1);
        ExerciseEntry entry1 = new ExerciseEntry("home", r1, "12:00");
        Boolean added1 = schedule.addExerciseEntry(entry1, "monday");


        schedule.removeExerciseEntry("monday");
        assertFalse(schedule.dayOccupied("monday"));
        assertEquals(0, schedule.getTotalExerciseSeconds());


    }

    @Test
    void testRemoveExerciseEntryOnOneOfTwoDays() {
        Exercise e1 = new Exercise("squats", 30);
        Routine r1 = new Routine("g");
        r1.addExercise(e1);
        ExerciseEntry entry1 = new ExerciseEntry("home", r1, "12:00");
        schedule.addExerciseEntry(entry1, "monday");

        Exercise e2 = new Exercise("squats", 100);
        Routine r2 = new Routine("e");
        r2.addExercise(e2);
        ExerciseEntry entry2 = new ExerciseEntry("home", r2, "11:00");
        schedule.addExerciseEntry(entry2, "thursday");


        assertEquals(130, schedule.getTotalExerciseSeconds());
        schedule.removeExerciseEntry("monday");
        assertFalse(schedule.dayOccupied("monday"));
        assertEquals(100, schedule.getTotalExerciseSeconds());


    }

    @Test
    void testDayOccupiedEmpty() {
        boolean occupied = schedule.dayOccupied("friday");
        assertFalse(occupied);
    }

    @Test
    void testDayOccupiedOccupied() {
        Routine r1 = new Routine("hh");
        ExerciseEntry entry1 = new ExerciseEntry("home", r1, "12:00");
        Boolean added1 = schedule.addExerciseEntry(entry1, "friday");

        boolean occupied = schedule.dayOccupied("friday");
        assertTrue(occupied);
    }

    @Test
    void testGetTotalExerciseSecondsEmpty() {
        assertEquals(0, schedule.getTotalExerciseSeconds());
    }

    @Test
    void testGetTotalExerciseSecondsNonEmptyZeroSecondsRoutine() {
        Routine r1 = new Routine("b");
        ExerciseEntry entry1 = new ExerciseEntry("home", r1, "12:00");
        Boolean added1 = schedule.addExerciseEntry(entry1, "friday");
        assertEquals(0, schedule.getTotalExerciseSeconds());
    }

    @Test
    void testGetTotalExerciseSecondsNonEmptyZMultipleSecondsRoutine() {
        Routine r1 = new Routine("hello");
        Exercise e1 = new Exercise("pushups", 20);
        r1.addExercise(e1);
        ExerciseEntry entry1 = new ExerciseEntry("home", r1, "12:00");
        Boolean added1 = schedule.addExerciseEntry(entry1, "friday");
        assertEquals(20, schedule.getTotalExerciseSeconds());
    }

    @Test
    void testGetWeeklySchedule() {
        ArrayList<ExerciseEntry> wSched = schedule.getWeeklySchedule();
        ArrayList<ExerciseEntry> test = new ArrayList<ExerciseEntry>();
        for (int i = 0; i <= 6; i++) {
            test.add(i, null);
        }
        assertEquals(test, wSched);
    }
}