package oreo;

import java.util.ArrayList;

import oreo.task.Task;
import oreo.ui.Ui;
import oreo.util.Parser;
import oreo.util.Storage;
import oreo.util.TaskList;

/**
 * The main class of the Oreo chatbot.
 */
public class Oreo {
    private final Parser parser;
    private final TaskList tasks;

    /**
     * Initializes the UI, Parser, Storage and TaskList components of the chatbot.
     * @throws OreoException Custom exception made for the chatbot.
     */
    public Oreo() throws OreoException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        parser = new Parser(ui, storage);
        ArrayList<Task> loadedTasks = storage.loadTasks();
        if (loadedTasks != null) { // if a saved tasks file exists
            tasks = new TaskList(loadedTasks);
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
