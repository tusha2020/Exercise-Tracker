
import model.Exercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ExerciseTest {
    private Exercise e1;

    @BeforeEach
    void initialize() {
        e1 = new Exercise("pushups", 30);
    }

    @Test
    void testConstructor() {
        String name = e1.getName();
        Integer time = e1.getSeconds();
        boolean completed = e1.getCompletion();

        assertEquals("pushups", name);
        assertEquals(30, time);
        assertFalse(completed);
    }

    @Test
    void testCompleteExercise() {
        e1.completeExercise();
        assertTrue(e1.getCompletion());
    }

    @Test
    void testCompleteExerciseTwice() {
        e1.completeExercise();
        e1.completeExercise();
        assertTrue(e1.getCompletion());
    }

    @Test
    void testGetName() {
        Exercise e2 = new Exercise("squats", 20);
        String name = e1.getName();
        String name2 = e2.getName();

        assertEquals("pushups", name);
        assertEquals("squats", name2);
    }


    @Test
    void testGetTime() {
        Exercise e2 = new Exercise("squats", 20);
        Integer time = e1.getSeconds();
        Integer time2 = e2.getSeconds();

        assertEquals(30, time);
        assertEquals(20, time2);
    }

    @Test
    void testGetCompletion() {
        assertFalse(e1.getCompletion());
        e1.completeExercise();
        assertTrue(e1.getCompletion());
    }
}
