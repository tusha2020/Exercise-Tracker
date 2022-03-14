import model.Exercise;
import model.ExerciseEntry;
import model.Routine;
import model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This [class] references code from JsonSerializationDemo
// Link: [https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git]
public class JsonReaderTest {

    private Schedule schedule;
    ArrayList<Routine> routines;
    ExerciseEntry entry;
    Routine r1;
    Exercise exercise;

    @BeforeEach
    void initialize() {
        schedule = new Schedule();
        routines =  new ArrayList<>();
        r1 = new Routine("hard");
        entry = new ExerciseEntry("home", r1, "12");
        exercise = new Exercise("Jumping", 30);
        r1.addExercise(exercise);
    }


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            schedule = reader.readSchedule();
            routines = reader.readRoutines();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testReaderEmptyScheduleRoutines() {
        JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");
        try {
            schedule = reader.readSchedule();
            routines = reader.readRoutines();
            ArrayList<ExerciseEntry> weeklySchedule = schedule.getWeeklySchedule();

            assertEquals(7, weeklySchedule.size());
            for (ExerciseEntry e: weeklySchedule) {
                assertEquals(null, e);
            }
            assertEquals(0, routines.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderNormalScheduleRoutines() {
        JsonReader reader = new JsonReader("./data/testWriterRegularSchedule.json");
        schedule.addExerciseEntry(entry, "monday");
        routines.add(r1);
        routines.add(r1);
        try {
            schedule = reader.readSchedule();
            ArrayList<Routine> routines = reader.readRoutines();
            ArrayList<ExerciseEntry> weeklySchedule = schedule.getWeeklySchedule();

            assertEquals(7, weeklySchedule.size());
            ExerciseEntry entry1 = schedule.thisDaysExercise("monday");

            assertEquals(entry.getTime(), schedule.thisDaysExercise("monday").getTime());
            assertEquals(entry.getPlace(), schedule.thisDaysExercise("monday").getPlace());
            assertEquals(entry.getRoutine().getName(), schedule.thisDaysExercise("monday").getRoutine().getName());


            assertEquals(2, routines.size());
            assertEquals("hard", routines.get(1).getName());
            ArrayList<Exercise> exercisesSecondRoutine = routines.get(0).getExercises();
            Exercise firstExercise = exercisesSecondRoutine.get(0);
            assertEquals(exercise.getName(), firstExercise.getName());
            assertEquals(exercise.getSeconds(), firstExercise.getSeconds());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
