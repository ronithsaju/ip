package oreo.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a name, completion status, start date and end date.
 */
public class Event extends Task {

    /** Start date of the event */
    protected LocalDate from;
    /** End date of the event */
    protected LocalDate to;
    /** Formatter for date to display */
    private static final DateTimeFormatter DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Event(String name, LocalDate from, LocalDate to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String saveFormat() {
        return "E" + "|" + super.saveFormat() + "|" + from + "|" + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DISPLAY) + " to: " + to.format(DISPLAY) + ")";
    }
}
