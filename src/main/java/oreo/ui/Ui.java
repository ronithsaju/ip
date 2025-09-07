package oreo.ui;

import oreo.task.Task;
import oreo.util.TaskList;

/**
 * Handles all user interactions (input commands) and outputs of the chatbot.
 */
public class Ui {
    /**
     * Returns a goodbye for the user.
     */
    public String byeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns all the tasks so far.
     * @param tl List of all the tasks so far.
     */
    public String listMessage(TaskList tl) {
        StringBuilder res = new StringBuilder();
        for (Task task : tl.getTasks()) {
            res.append((tl.getTaskIndex(task) + 1) + "." + task + "\n");
        }
        return "Here are the tasks in your list:\n"
                + res;
    }

    /**
     * Returns message that the task is marked as completed.
     * @param t Task to be marked as completed.
     */
    public String markMessage(Task t) {
        return "Nice! I've marked this task as done:\n"
                + t;
    }

    /**
     * Returns message that the task is marked as uncompleted.
     * @param t Task to be marked as uncompleted.
     */
    public String unmarkMessage(Task t) {
        return "OK, I've marked this task as not done yet:\n"
                + t;
    }

    /**
     * Returns message that the task has been added and the number of tasks so far.
     * @param t Task to be added.
     * @param tl List of all the tasks so far.
     */
    public String taskMessage(Task t, TaskList tl) {
        return "Got it. I've added this task:\n"
                + t + "\nNow you have " + tl.getSize() + " tasks in the list.";
    }

    /**
     * Returns message that the task has been deleted and the number of tasks so far.
     * @param t Task to be deleted.
     * @param tl List of all the tasks so far.
     */
    public String deleteMessage(Task t, TaskList tl) {
        return "Noted. I've removed this task:\n"
                + t + "\nNow you have " + tl.getSize() + " tasks in the list.";
    }

    /**
     * Returns the tasks in the provided task list.
     * @param matchesTl List of tasks that matches the user provided keyword.
     */
    public String findMessage(TaskList matchesTl) {
        StringBuilder res = new StringBuilder();
        for (Task task : matchesTl.getTasks()) {
            res.append((matchesTl.getTaskIndex(task) + 1) + "." + task + "\n");
        }
        return "Here are the matching tasks in your list:\n"
                + res;
    }

    /**
     * Returns an invalid input message.
     */
    public String invalidInputMessage() {
        return "Invalid input!\n" + "Please use any of the following commands: "
                + "list, mark, unmark, todo, deadline, event, delete, find or bye";
    }
}
