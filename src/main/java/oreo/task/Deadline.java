package oreo.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a name, completion status and a due date.
 */
public class Deadline extends Task {

    /** Formatter for date to display */
    private static final DateTimeFormatter DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy");
    /** Due date of the deadline task */
    protected LocalDate by;

    /**
     * Initializes a Deadline object with a name and due date.
     * @param name Name of the deadline.
     * @param by Due date of the deadline.
     */
    public Deadline(String name, LocalDate by) {
        super(name);
        this.by = by;
    }

    @Override
    public String saveFormat() {
        return "D" + "|" + super.saveFormat() + "|" + by + "|" + (note != null ? note.getInfo() : "");
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY) + ")";
    }
}
