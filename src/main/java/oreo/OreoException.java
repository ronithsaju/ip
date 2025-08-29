package oreo;

public class OreoException extends Exception {
    public OreoException(String msg) {
        super(msg);
    }

    public OreoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
