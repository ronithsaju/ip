public class Task {
    protected String name;
    protected boolean isCompleted;

    public Task(String name) {
        this.name = name;
        this.isCompleted = false;
    }

    public String getCompletionStatusIcon() {
        return (isCompleted ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return "[" + getCompletionStatusIcon() + "] " + name;
    }
}
