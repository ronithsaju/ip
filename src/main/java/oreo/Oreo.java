package oreo;

import oreo.ui.Ui;
import oreo.util.Parser;
import oreo.util.Storage;
import oreo.util.TaskList;

/**
 * The main class of the Oreo chatbot.
 */
public class Oreo {
    private final Ui ui;
    private final Parser parser;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Initializes the UI, Parser, Storage and TaskList components of the chatbot.
     * @param filePath Path to previously saved tasks list.
     * @throws OreoException Custom exception made for the chatbot.
     */
    public Oreo(String filePath) throws OreoException {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser(ui, storage);
        if (storage.loadTasks() != null) { // if a saved tasks file exists
            tasks = new TaskList(storage.loadTasks());
        } else {
            tasks = new TaskList();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) throws OreoException {
        return parser.parse(input, tasks);
    }
}
