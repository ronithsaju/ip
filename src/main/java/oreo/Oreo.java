package oreo;

import oreo.ui.Ui;
import oreo.util.Parser;
import oreo.util.Storage;
import oreo.util.TaskList;

public class Oreo {
    private final Ui ui;
    private final Parser parser;
    private final Storage storage;
    private final TaskList tasks;

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

    public void run() throws Exception {
        ui.welcomeMessage();
        while (!parser.getIsExit()) {
            String userInput = ui.readCommand();
            parser.parse(userInput, tasks, ui, storage);
        }
    }

    public static void main(String[] args) throws Exception {
        new Oreo("./data/oreo.txt").run();
    }
}
