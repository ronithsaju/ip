package oreo.task;

/**
 * Represents a note for a task
 */
public class Note {
    private final String info;

    /**
     * Initializes a note with some text.
     * @param info The text to be in the note.
     */
    public Note(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
