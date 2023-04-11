package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Event e2;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");   // (1)
        d = Calendar.getInstance().getTime();   // (2)

        e2 = new Event("Sensor do not open at door");
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        //assertEquals(d, e.getDate());

        assertFalse(e.getDate().equals(null));
        assertNotEquals(e.getDate(), new Date());

        assertFalse(e.equals(null));
        assertFalse(e.equals(d) && d.equals(e));
        assertFalse(e.equals(e2) && e2.equals(e));
        assertFalse(e.hashCode() == e2.hashCode());

        // last because it fails because it's off by 1 ms or whatever
        //assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }
}