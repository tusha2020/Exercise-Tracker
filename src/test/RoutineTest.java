import model.Exercise;
import model.Routine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoutineTest {
    private Routine routine;

    @BeforeEach
    void initialize() {
        routine = new Routine("r1");
    }


    @Test
    void testConstructor() {
        double totalSeconds = routine.getTotalSeconds();
        assertEquals(0, totalSeconds);
        assertEquals(routine.getExercises(), new ArrayList<Exercise>());
        assertEquals("r1", routine.getName());
    }

    @Test
    void testAddExerciseOne() {
        Exercise e1 = new Exercise("push-ups", 30);
        routine.addExercise(e1);
        Exercise firstOnRoutine = routine.getExercise();

        assertEquals(e1, firstOnRoutine);
        assertEquals(30, routine.getTotalSeconds());
        assertEquals(1, routine.routineSize());
    }

    @Test
    void testAddExerciseTwo() {
        Exercise e1 = new Exercise("push-ups", 30);
        routine.addExercise(e1);
        Exercise e2 = new Exercise("squats", 60);
        routine.addExercise(e2);
        Exercise firstOnRoutine = routine.getExercise();

        assertEquals(firstOnRoutine, e2);
        assertEquals(90, routine.getTotalSeconds());
        assertEquals(2, routine.routineSize());
    }

    @Test
    void testGetExercise() {
        Exercise e1 = new Exercise("push-ups", 30);
        routine.addExercise(e1);
        Exercise e2 = new Exercise("squats", 60);
        routine.addExercise(e2);
        Exercise firstOnRoutine = routine.getExercise();

        assertEquals(firstOnRoutine, e2);
    }

    @Test
    void testGetTotalSecondsEmpty() {
       double total =  routine.getTotalSeconds();
       assertEquals(0, total);
    }

    @Test
    void testGetTotalSecondsMany() {
        Exercise e1 = new Exercise("push-ups", 30);
        routine.addExercise(e1);
        Exercise e2 = new Exercise("squats", 60);
        routine.addExercise(e2);

        assertEquals(90, routine.getTotalSeconds());
    }

    @Test
    void testRoutineSizeEmpty() {
        assertEquals(0, routine.routineSize());
    }

    @Test
    void testRoutineSizeNonEmpty() {
        Exercise e1 = new Exercise("push-ups", 30);
        routine.addExercise(e1);
        assertEquals(1, routine.routineSize());
    }


    @Test
    void testRemoveExerciseOne() {
        Exercise e1 = new Exercise("push-ups", 30);
        routine.addExercise(e1);

        routine.removeExercise(e1);
        assertEquals(0, routine.getTotalSeconds());
        assertEquals(0, routine.routineSize());

    }

    @Test
    void testRemoveExerciseTwo() {
        Exercise e1 = new Exercise("push-ups", 30);
        routine.addExercise(e1);
        Exercise e2 = new Exercise("squats", 1000);
        routine.addExercise(e2);

        routine.removeExercise(e1);
        assertEquals(1000, routine.getTotalSeconds());
        assertEquals(1, routine.routineSize());

    }

    @Test
    void testGetName() {
        assertEquals("r1", routine.getName());
    }

    @Test
    void testReturnExercises() {
        assertEquals(new ArrayList<Exercise>(), routine.getExercises());
    }
}
