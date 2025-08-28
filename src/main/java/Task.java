public class Task {
    protected String name;
    protected boolean isCompleted;

    public Task(String name) {
        this.name = name;
    }

    public String getCompletionStatusIcon() {
        return (isCompleted ? "X" : " "); // mark done task with X
    }

    public String saveFormat() {
        int completionStatus = isCompleted ? 1 : 0;
        return completionStatus + "|" + name;
    }

    @Override
    public String toString() {
        return "[" + getCompletionStatusIcon() + "] " + name;
    }
}
