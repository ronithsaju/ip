package oreo.task;

/**
 * Represents a Todo task with a name and a completion status.
 */
public class Todo extends Task {

    public Todo(String name) {
        super(name);
    }

    @Override
    public String saveFormat() {
        return "T" + "|" + super.saveFormat();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
