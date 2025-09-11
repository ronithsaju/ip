package oreo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests the toString() and saveFormat() methods in the Event class
 */
public class EventTest {
    @Test
    public void toString_basicEvent_success() {
        LocalDate from = LocalDate.of(2025, 9, 5);
        LocalDate to = LocalDate.of(2025, 9, 5);
        Event e = new Event("CS2103T Week 4 Lecture", from, to);
        assertEquals("[E][ ] CS2103T Week 4 Lecture (from: Sep 05 2025 to: Sep 05 2025)", e.toString());
    }

    @Test
    public void saveFormat_completedEvent_success() {
        LocalDate from = LocalDate.of(2025, 9, 1);
        LocalDate to = LocalDate.of(2025, 9, 5);
        Event e = new Event("Week 4", from, to);
        e.setIsCompleted(true);
        assertEquals("E|1|Week 4|2025-09-01|2025-09-05|", e.saveFormat());
    }
}
