package oreo.task;

/**
 * Represents a Todo task with a name and a completion status.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given name.
     * @param name The name of the Todo task.
     */
    public Todo(String name) {
        super(name);
    }

    @Override
    public String saveFormat() {
        return "T" + "|" + super.saveFormat() + "|" + (note != null ? note.getInfo() : "");
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
