package oreo.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDate by;
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
