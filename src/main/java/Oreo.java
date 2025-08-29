import java.util.ArrayList;

public class Oreo {
    private final Storage storage;
    private final Ui ui;
    private final Parser parser;
    private final ArrayList<Task> tasks;

    public Oreo(String filePath) throws OreoException {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        if (storage.loadTasks() != null) { // if a saved tasks file exists
            tasks = new ArrayList<>(storage.loadTasks());
        } else {
            tasks = new ArrayList<>();
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
