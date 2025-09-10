package oreo.util;

import oreo.OreoException;
import oreo.gui.Main;
import oreo.task.Deadline;
import oreo.task.Event;
import oreo.task.Task;
import oreo.task.Todo;
import oreo.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user input commands and performs the respective actions needed
 */
public class Parser {
    private final Ui ui;
    private final Storage storage;

    /** Formatter for date received from user */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
        if (userInput.equals("bye")) {
            Main.end();
            return ui.byeMessage();
        } else if (userInput.equals("list")) {
            return ui.listMessage(tasks);
        } else if (userInput.startsWith("mark")) {
            int taskNum = extractNumber(userInput);
            Task t = tasks.getTask(taskNum - 1);
            t.setIsCompleted(true);
            storage.saveTasks(tasks);
            return ui.markMessage(t);
        } else if (userInput.startsWith("unmark")) {
            int taskNum = extractNumber(userInput);
            Task t = tasks.getTask(taskNum - 1);
            t.setIsCompleted(false);
            storage.saveTasks(tasks);
            return ui.unmarkMessage(t);
        } else if (userInput.startsWith("todo")) {
            Task t = new Todo(userInput.substring(5));
            tasks.addTask(t);
            storage.saveTasks(tasks);
            return ui.taskMessage(t, tasks);
        } else if (userInput.startsWith("deadline")) {
            try {
                // extracts task name and due date
                String name = userInput.substring(9, userInput.indexOf("/by") - 1);
                String byStr = userInput.substring(userInput.indexOf("/by") + 4);

                try {
                    LocalDate byDateTime = LocalDate.parse(byStr, DATE_FORMAT);
                    Task t = new Deadline(name, byDateTime);
                    tasks.addTask(t);
                    storage.saveTasks(tasks);
                    return ui.taskMessage(t, tasks);
                } catch (DateTimeParseException e) {
                    throw new OreoException("Invalid date format. "
                            + "Please follow DD/MM/YYYY. e.g. 17/11/2002", e);
                }
            } catch (OreoException ex) {
                // rethrow DateTimeParseException
                throw ex;
            } catch (Exception ex) {
                // unexpected errors
                throw new OreoException("Failed to create deadline task", ex);
            }
        } else if (userInput.startsWith("event")) {
            try {
                // extracts task name, start date and end date
                String name = userInput.substring(6, userInput.indexOf("/from") - 1);
                String fromStr = userInput.substring(userInput.indexOf("/from") + 6,
                        userInput.indexOf("/to") - 1);
                String toStr = userInput.substring(userInput.indexOf("/to") + 4);

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
            } catch (OreoException ex) {
                // rethrow DateTimeParseException
                throw ex;
            } catch (Exception ex) {
                //unexpected errors
                throw new OreoException("Failed to create event task", ex);
            }
        } else if (userInput.startsWith("delete")) {
            int taskNum = extractNumber(userInput);
            Task t = tasks.getTask(taskNum - 1);
            tasks.removeTask(t);
            storage.saveTasks(tasks);
            return ui.deleteMessage(t, tasks);
        } else if (userInput.startsWith("find")) {
            String keyword = userInput.substring(5);
            TaskList matchingTasks = tasks.findTaskByKeywordSearch(keyword);
            return ui.findMessage(matchingTasks);
        } else {
            // user input is none of the valid commands above
            return ui.invalidInputMessage();
        }
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
}
