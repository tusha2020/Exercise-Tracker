import model.Exercise;
import model.ExerciseEntry;
import model.Routine;
import model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
// This [class] references code from JsonSerializationDemo
// Link: [https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git]

public class JsonWriterTest {
    Schedule testSc;
    ArrayList<Routine> routines;
    ExerciseEntry entry;
    Routine r1;
    Exercise exercise;


    @BeforeEach
    void initialize() {
        testSc = new Schedule();
        routines =  new ArrayList<>();
        r1 = new Routine("hard");
        entry = new ExerciseEntry("home", r1, "12");
        exercise = new Exercise("Jumping", 30);
        r1.addExercise(exercise);
    }


    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testWriterEmptyScheduleEmptyRoutines() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySchedule.json");
            writer.open();
            writer.write(testSc, routines);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");

            testSc = reader.readSchedule();
            routines = reader.readRoutines();
            ArrayList<ExerciseEntry> weeklySchedule = testSc.getWeeklySchedule();

            assertEquals(7, weeklySchedule.size());
            assertEquals(0, routines.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterRegularScheduleRoutines() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterRegularSchedule.json");
            routines.add(r1);
            routines.add(r1);
            testSc.addExerciseEntry(entry, "monday");
            writer.open();
            writer.write(testSc, routines);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterRegularSchedule.json");

            testSc = reader.readSchedule();
            routines = reader.readRoutines();
            ArrayList<ExerciseEntry> weeklySchedule = testSc.getWeeklySchedule();
            ArrayList<Exercise> exercisesFirstRoutine = routines.get(0).getExercises();

            assertEquals(entry.getTime(), testSc.thisDaysExercise("monday").getTime());
            assertEquals(entry.getPlace(), testSc.thisDaysExercise("monday").getPlace());
            assertEquals(entry.getRoutine().getName(), testSc.thisDaysExercise("monday").getRoutine().getName());
            assertEquals(7, weeklySchedule.size());
            assertEquals(2, routines.size());
            assertEquals("hard", routines.get(0).getName());
            Exercise firstExercise = exercisesFirstRoutine.get(0);
            assertEquals(exercise.getName(), firstExercise.getName());
            assertEquals(exercise.getSeconds(), firstExercise.getSeconds());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
