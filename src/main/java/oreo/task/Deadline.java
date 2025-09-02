package oreo.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a name, completion status and a due date.
 */
public class Deadline extends Task {

    /** Due date of the deadline task */
    protected LocalDate by;
    /** Formatter for date to display */
    private static final DateTimeFormatter DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String name, LocalDate by) {
        super(name);
        this.by = by;
    }

    @Override
    public String saveFormat() {
        return "D" + "|" + super.saveFormat() + "|" + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(DISPLAY) + ")";
    }
}
