import model.ExerciseEntry;
import model.Routine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ExerciseEntryTest {
    private ExerciseEntry entry;
    private Routine r1;

    @BeforeEach
    void initialize() {
        r1 = new Routine("Test routine");
        entry = new ExerciseEntry("home", r1, "1:00");
    }

    @Test
    void testConstructor() {
        assertEquals("home", entry.getPlace());
        assertEquals(r1, entry.getRoutine());
        assertEquals("1:00", entry.getTime());
    }

    @Test
    void testChangeTime() {
        entry.changeTime("2:00");
        assertEquals("2:00", entry.getTime());
    }

    @Test
    void testChangeTimeLessThan() {
        entry.changeTime("0");
        assertEquals("0", entry.getTime());
    }

    @Test
    void testChangePlace() {
        entry.changePlace("gym");
        assertEquals("gym", entry.getPlace());
    }

    @Test
    void testChangePlaceSame() {
        entry.changePlace("home");
        assertEquals("home", entry.getPlace());
    }

    @Test
    void testChangeRoutine() {
        Routine r2 = new Routine("hi");
        entry.changeRoutine(r2);

        assertEquals(r2, entry.getRoutine());
    }

    @Test
    void testChangeRoutineSame() {
        entry.changeRoutine(r1);
        assertEquals(r1, entry.getRoutine());
    }

    @Test
    void testGetPlace() {
        String place = entry.getPlace();
        assertEquals("home", place);
    }


    @Test
    void testGetTime() {
        String time = entry.getTime();
        assertEquals("1:00", time);
    }

    @Test
    void testGetRoutine() {
        Routine routine = entry.getRoutine();
        assertEquals(r1, routine);
    }

}
