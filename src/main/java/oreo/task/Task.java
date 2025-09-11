package oreo.task;

/**
 * Represents a Task with a name and a completion status.
 */
public class Task {
    protected String name;
    protected boolean isCompleted;
    protected Note note = null;

    /**
     * Creates a new Task.
     * @param name Name of the task.
     */
    public Task(String name) {
        assert name != null : "No name provided for task";
        this.name = name;
    }

    /**
     * Marks a completed task with X.
     * To be used for toString() method below.
     * @return X if completed, 1 empty space if not completed.
     */
    public String getCompletionStatusIcon() {
        return (isCompleted ? "X" : " ");
    }

    /**
     * Formats task details into the correct format,
     * for storage into the saved task list file.
     * @return String representation for file storage.
     */
    public String saveFormat() {
        int completionStatus = (isCompleted ? 1 : 0);
        return completionStatus + "|" + name;
    }

    public void setIsCompleted(boolean x) {
        isCompleted = x;
    }

    public String getName() {
        return name;
    }

    /**
     * Creates a note for the task and initializes it with some text.
     * @param info The text to be in the note.
     */
    public void setNote(String info) {
        this.note = new Note(info);
    }

    /**
     * Returns the note for the task.
     */
    public Note getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "[" + getCompletionStatusIcon() + "] " + name;
    }
}
