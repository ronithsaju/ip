package oreo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import oreo.OreoException;
import oreo.gui.Main;
import oreo.task.Deadline;
import oreo.task.Event;
import oreo.task.Task;
import oreo.task.Todo;
import oreo.ui.Ui;

/**
 * Parses user input commands and performs the respective actions needed
 */
public class Parser {
    /** Formatter for date received from user */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Ui ui;
    private final Storage storage;

    /**
     * Initializes a parser with UI and storage.
     * @param ui User Interactions (UI) handler.
     * @param storage Saved tasks list storage and retrieval handler.
     */
    public Parser(Ui ui, Storage storage) {
        assert ui != null : "Parser unable to find Ui";
        assert storage != null : "Parser unable to find Storage";
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Handles all (valid and invalid) user commands and runs the respective actions needed
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @throws OreoException Custom exception made for the chatbot.
     */
    public String parse(String userInput, TaskList tasks) throws OreoException {
        switch (getCommand(userInput)) {
        case "bye":
            Main.end();
            return ui.byeMessage();
        case "list":
            return ui.listMessage(tasks);
        case "mark":
            return markCommand(userInput, tasks);
        case "unmark":
            return unmarkCommand(userInput, tasks);
        case "todo":
            return handleTodoCommand(userInput, tasks);
        case "deadline":
            return handleDeadlineCommand(userInput, tasks);
        case "event":
            return handleEventCommand(userInput, tasks);
        case "delete":
            return deleteCommand(userInput, tasks);
        case "find":
            return findCommand(userInput, tasks);
        case "setnote":
            return setNoteCommand(userInput, tasks);
        case "getnote":
            return getNoteCommand(userInput, tasks);
        default: // user input is none of the valid commands above
            throw new OreoException("Invalid input!\n" + "Please use any of the following commands: "
                    + "list, mark, unmark, todo, deadline, event, delete, find, "
                    + "setnote, getnote or bye");
        }
    }

    /**
     * Extracts and returns command from user input String.
     * @param userInput User input command.
     * @return Command extracted from user input.
     */
    private String getCommand(String userInput) {
        if (userInput.contains(" ")) {
            return userInput.substring(0, userInput.indexOf(" "));
        }
        return userInput;
    }

    /**
     * Marks a task as completed.
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @return Message to be shown to user after marking task.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private String markCommand(String userInput, TaskList tasks) throws OreoException {
        int taskNum = extractNumber(userInput);
        validateTaskNumber(taskNum, tasks);
        Task t = tasks.getTask(taskNum - 1);
        t.setIsCompleted(true);
        storage.saveTasks(tasks);
        return ui.markMessage(t);
    }

    /**
     * Marks a task as not completed.
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @return Message to be shown to user after unmarking task.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private String unmarkCommand(String userInput, TaskList tasks) throws OreoException {
        int taskNum = extractNumber(userInput);
        validateTaskNumber(taskNum, tasks);
        Task t = tasks.getTask(taskNum - 1);
        t.setIsCompleted(false);
        storage.saveTasks(tasks);
        return ui.unmarkMessage(t);
    }

    /**
     * Adds a Todo task to the list of tasks.
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @return Message to be shown to user after adding task.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private String handleTodoCommand(String userInput, TaskList tasks) throws OreoException {
        String name = userInput.substring("todo".length()).trim();
        if (name.isEmpty()) {
            throw new OreoException("The name of a todo cannot be empty.");
        }
        Task t = new Todo(name);
        tasks.addTask(t);
        storage.saveTasks(tasks);
        return ui.taskMessage(t, tasks);
    }

    /**
     * Adds a Deadline task to the list of tasks.
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @return Message to be shown to user after adding task.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private String handleDeadlineCommand(String userInput, TaskList tasks) throws OreoException {
        // extracts task name and due date
        String[] parts = userInput.split(" /by ");
        if (parts.length < 2) {
            throw new OreoException("Please provide both a deadline name and due date.");
        }
        String name = parts[0].substring("deadline".length()).trim();
        String byStr = parts[1].trim();
        if (name.isEmpty() || byStr.isEmpty()) {
            throw new OreoException("The name and due date of a deadline cannot be empty. "
                    + "Please follow the format: deadline <task name> /by <due date>. "
                    + "e.g. deadline return book /by 17-11-2025");
        }

        try {
            LocalDate byDateTime = LocalDate.parse(byStr, DATE_FORMAT);
            Task t = new Deadline(name, byDateTime);
            tasks.addTask(t);
            storage.saveTasks(tasks);
            return ui.taskMessage(t, tasks);
        } catch (DateTimeParseException e) {
            throw new OreoException("Invalid date format. "
                    + "Please follow DD-MM-YYYY. e.g. 17-11-2002", e);
        }
    }

    /**
     * Adds an Event task to the list of tasks.
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @return Message to be shown to user after adding task.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private String handleEventCommand(String userInput, TaskList tasks) throws OreoException {
        // extracts task name, start date and end date
        String[] parts = userInput.split(" /from | /to ");
        if (parts.length < 3 || parts[1].isEmpty() || parts[2].isEmpty()) {
            throw new OreoException("Please provide an event name, start date and end date.");
        }
        String name = parts[0].substring("event".length()).trim();
        String fromStr = parts[1].trim();
        String toStr = parts[2].trim();
        if (name.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
            throw new OreoException("The name, start date and end date of an event cannot be empty. "
                    + "Please follow the format: event <task name> /from <start date> /to <end date>. "
                    + "e.g. event hackathon /from 17-11-2025 /to 18-11-2025");
        }

        try {
            LocalDate fromDateTime = LocalDate.parse(fromStr, DATE_FORMAT);
            LocalDate toDateTime = LocalDate.parse(toStr, DATE_FORMAT);
            Task t = new Event(name, fromDateTime, toDateTime);
            tasks.addTask(t);
            storage.saveTasks(tasks);
            return ui.taskMessage(t, tasks);
        } catch (DateTimeParseException e) {
            throw new OreoException("Invalid date format. "
                    + "Please follow DD-MM-YYYY. e.g. 17-11-2002", e);
        }
    }

    /**
     * Deletes a task from the list of tasks.
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @return Message to be shown to user after deleting task.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private String deleteCommand(String userInput, TaskList tasks) throws OreoException {
        int taskNum = extractNumber(userInput);
        validateTaskNumber(taskNum, tasks);
        Task t = tasks.getTask(taskNum - 1);
        tasks.removeTask(t);
        storage.saveTasks(tasks);
        return ui.deleteMessage(t, tasks);
    }

    /**
     * Finds tasks that match a keyword search.
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @return Message to be shown to user after finding tasks.
     */
    private String findCommand(String userInput, TaskList tasks) {
        String keyword = userInput.substring("find".length()).trim();
        TaskList matchingTasks = tasks.findTaskByKeywordSearch(keyword);
        return ui.findMessage(matchingTasks);
    }

    /**
     * Sets a note to a task.
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @return Message to be shown to user after setting note.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private String setNoteCommand(String userInput, TaskList tasks) throws OreoException {
        String[] parts = userInput.split(" ", 3);
        if (parts.length < 3) {
            throw new OreoException("Please provide both a task number and note content.");
        }
        String info = parts[2].trim();
        if (info.isEmpty()) {
            throw new OreoException("The content of a note cannot be empty.");
        }
        int taskNum = extractNumber(parts[1]);
        validateTaskNumber(taskNum, tasks);
        Task t = tasks.getTask(taskNum - 1);
        t.setNote(info);
        storage.saveTasks(tasks);
        return ui.setNoteMessage(t);
    }

    /**
     * Gets the note of a task (if any).
     * @param userInput User input command.
     * @param tasks List of tasks.
     * @return Message to be shown to user after getting note.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private String getNoteCommand(String userInput, TaskList tasks) throws OreoException {
        int taskNum = extractNumber(userInput);
        validateTaskNumber(taskNum, tasks);
        Task t = tasks.getTask(taskNum - 1);
        return ui.getNoteMessage(t);
    }

    /**
     * Extracts and returns number from any input String.
     * @param input User input command.
     * @return Integer extracted from user input.
     * @throws OreoException Custom exception made for the chatbot thrown if there is no number inside input.
     */
    public static int extractNumber(String input) throws OreoException {
        try {
            return Integer.parseInt(input.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            throw new OreoException("Please provide a valid task number.");
        }
    }

    /**
     * Throws exception if the task number provided is invalid.
     * @param taskNum Task number provided by user.
     * @param tasks List of tasks.
     * @throws OreoException Custom exception made for the chatbot.
     */
    public void validateTaskNumber(int taskNum, TaskList tasks) throws OreoException {
        if (taskNum < 1 || taskNum > tasks.getSize()) {
            throw new OreoException("Please provide a valid task number.");
        }
    }
}
