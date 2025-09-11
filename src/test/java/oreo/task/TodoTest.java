package oreo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the toString() and saveFormat() methods in the Todo class
 */
public class TodoTest {
    @Test
    public void toString_basicTodo_success() {
        assertEquals("[T][ ] read book", new Todo("read book").toString());
    }

    @Test
    public void saveFormat_completedTodo_success() {
        Task sleep = new Todo("sleep");
        sleep.setIsCompleted(true);
        assertEquals("T|1|sleep|", sleep.saveFormat());
    }
}
