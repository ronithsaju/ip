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
        return "Here are the tasks in your list:\n" + listTasks(tl);
    }

    /**
     * Returns message that the task is marked as completed.
     * @param t Task to be marked as completed.
     */
    public String markMessage(Task t) {
        return "Nice! I've marked this task as done:\n" + t;
    }

    /**
     * Returns message that the task is marked as uncompleted.
     * @param t Task to be marked as uncompleted.
     */
    public String unmarkMessage(Task t) {
        return "OK, I've marked this task as not done yet:\n" + t;
    }

    /**
     * Returns message that the task has been added and the number of tasks so far.
     * @param t Task to be added.
     * @param tl List of all the tasks so far.
     */
    public String taskMessage(Task t, TaskList tl) {
        return "Got it. I've added this task:\n" + t
                + "\nNow you have " + tl.getSize() + " tasks in the list.";
    }

    /**
     * Returns message that the task has been deleted and the number of tasks so far.
     * @param t Task to be deleted.
     * @param tl List of all the tasks so far.
     */
    public String deleteMessage(Task t, TaskList tl) {
        return "Noted. I've removed this task:\n" + t
                + "\nNow you have " + tl.getSize() + " tasks in the list.";
    }

    /**
     * Returns the tasks in the provided task list.
     * @param matchesTl List of tasks that matches the user provided keyword.
     */
    public String findMessage(TaskList matchesTl) {
        return "Here are the matching tasks in your list:\n" + listTasks(matchesTl);
    }

    /**
     * Returns an invalid input message.
     */
    public String invalidInputMessage() {
        return "Invalid input!\n" + "Please use any of the following commands: "
                + "list, mark, unmark, todo, deadline, event, delete, find or bye";
    }

    /**
     * Returns message that the note has been added to the task.
     * @param t Task assigned to the note.
     */
    public String setNoteMessage(Task t) {
        return "Noted. I've added your note to this task:\n" + t;
    }

    /**
     * Returns the info in the note of the task.
     * @param t Task to get the info of the note from.
     */
    public String getNoteMessage(Task t) {
        if (t.getNote() == null) { // guard clause
            return "There is no note for this task";
        }
        return "Here is the note for the task:\n" + t.getNote().getInfo();
    }

    /**
     * Returns tasks numbered and listed out.
     * @param tl List of tasks.
     */
    public String listTasks(TaskList tl) {
        StringBuilder res = new StringBuilder();
        for (Task task : tl.getTasks()) {
            res.append((tl.getTaskIndex(task) + 1) + "." + task + "\n");
        }
        return res.toString();
    }
}
