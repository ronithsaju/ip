import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private boolean isExit;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Parser() {
        this.isExit = false;
    }

    public void parse(String userInput, TaskList tasks, Ui ui, Storage storage) throws OreoException {
        if (userInput.equals("bye")) {
            ui.byeMessage();
            isExit = true;
        } else if (userInput.equals("list")) {
            ui.listMessage(tasks);
        } else if (userInput.startsWith("mark")) {
            int taskNum = extractNumber(userInput);
            Task t = tasks.getTask(taskNum - 1);
            t.isCompleted = true;
            storage.saveTasks(tasks);
            ui.markMessage(t);
        } else if (userInput.startsWith("unmark")) {
            int taskNum = extractNumber(userInput);
            Task t = tasks.getTask(taskNum - 1);
            t.isCompleted = false;
            storage.saveTasks(tasks);
            ui.unmarkMessage(t);
        } else if (userInput.startsWith("todo")) {
            Task t = new Todo(userInput.substring(5));
            tasks.addTask(t);
            storage.saveTasks(tasks);
            ui.taskMessage(t, tasks);
        } else if (userInput.startsWith("deadline")) {
            try {
                String name = userInput.substring(9, userInput.indexOf("/by") -  1);
                String byStr = userInput.substring(userInput.indexOf("/by") + 4);

                try {
                    LocalDate byDateTime = LocalDate.parse(byStr, DATE_FORMAT);
                    Task t = new Deadline(name, byDateTime);
                    tasks.addTask(t);
                    storage.saveTasks(tasks);
                    ui.taskMessage(t, tasks);
                } catch (DateTimeParseException e) {
                    throw new OreoException("Invalid date format. " +
                            "Please follow DD/MM/YYYY. e.g. 17/11/2002", e);
                }
            } catch (OreoException ex) {
                // rethrow DateTimeParseException
                throw ex;
            } catch (Exception ex) {
                //unexpected errors
                throw new OreoException("Failed to create deadline task", ex);
            }
        } else if (userInput.startsWith("event")) {
            try {
                String name = userInput.substring(6, userInput.indexOf("/from") - 1);
                String fromStr = userInput.substring(userInput.indexOf("/from") + 6, userInput.indexOf("/to") - 1);
                String toStr = userInput.substring(userInput.indexOf("/to") + 4);

                try {
                    LocalDate fromDateTime = LocalDate.parse(fromStr, DATE_FORMAT);
                    LocalDate toDateTime = LocalDate.parse(toStr, DATE_FORMAT);
                    Task t = new Event(name, fromDateTime, toDateTime);
                    tasks.addTask(t);
                    storage.saveTasks(tasks);
                    ui.taskMessage(t, tasks);
                } catch (DateTimeParseException e) {
                    throw new OreoException("Invalid date format. " +
                            "Please follow DD/MM/YYYY. e.g. 17/11/2002", e);
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
            ui.deleteMessage(t, tasks);
        } else {
            ui.horizontalLine();
            throw new OreoException("Invalid input! Please write a todo, deadline or event task");
        }
    }

    // basic function to remove all non-numbers from input
    // with exception thrown if there is no number in input
    public static int extractNumber(String input) throws OreoException {
        try {
            return Integer.parseInt(input.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            throw new OreoException("Please provide a valid task number.");
        }
    }

    public boolean getIsExit() {
        return isExit;
    }
}
