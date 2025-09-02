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
        parser = new Parser();
        storage = new Storage(filePath);
        if (storage.loadTasks() != null) { // if a saved tasks file exists
            tasks = new TaskList(storage.loadTasks());
        } else {
            tasks = new TaskList();
        }
    }

    /**
     * Welcomes user and runs the chatbot operations.
     * @throws OreoException Custom exception made for the chatbot.
     */
    public void run() throws OreoException {
        ui.welcomeMessage();
        while (!parser.getIsExit()) {
            String userInput = ui.readCommand();
            parser.parse(userInput, tasks, ui, storage);
        }
    }

    /**
     * Runs the main execution loop of the chatbot.
     * @throws OreoException Custom exception made for the chatbot.
     */
    public static void main(String[] args) throws OreoException {
        new Oreo("./data/oreo.txt").run();
    }
}
