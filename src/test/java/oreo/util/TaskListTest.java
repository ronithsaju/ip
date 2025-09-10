package oreo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import oreo.task.Deadline;
import oreo.task.Task;
import oreo.task.Todo;

/**
 * Tests the getTask() and getSize() methods in the TaskList class
 */
public class TaskListTest {
    @Test
    public void getTask_basicTaskList_success() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read book"));
        Task t = new Deadline("complete tutorial", LocalDate.of(2025, 9, 5));
        tasks.add(t);
        TaskList tl = new TaskList(tasks);
        assertEquals(t, tl.getTask(1));
    }

    @Test
    public void getSize_taskListAfterTaskRemoved_success() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read book"));
        Task t = new Deadline("complete tutorial", LocalDate.of(2025, 9, 5));
        tasks.add(t);
        TaskList tl = new TaskList(tasks);
        tl.removeTask(t);
        assertEquals(1, tl.getSize());
    }
}
