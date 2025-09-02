package oreo;

/**
 * Custom exception made for the chatbot.
 */
public class OreoException extends Exception {
    public OreoException(String msg) {
        super(msg);
    }

    public OreoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
