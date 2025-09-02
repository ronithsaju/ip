package oreo.ui;

import oreo.task.Task;
import oreo.util.TaskList;

import java.util.Scanner;

/**
 * Handles all user interactions (input commands) and outputs of the chatbot.
 */
public class Ui {
    private final Scanner sc;

    /**
     * Creates a Ui object and initializes the scanner of user inputs.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Reads user inputs using scanner.
     * @return User input.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Prints out a single horizontal line.
     */
    public void horizontalLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints a welcome for the user.
     */
    public void welcomeMessage() {
        horizontalLine();
        System.out.println("Hello! I'm Oreo");
        System.out.println("What can I do for you?");
        horizontalLine();
    }

    /**
     * Prints a goodbye for the user.
     */
    public void byeMessage() {
        horizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        horizontalLine();
    }

    /**
     * Prints out all the tasks so far.
     * @param tl List of all the tasks so far.
     */
    public void listMessage(TaskList tl) {
        horizontalLine();
        System.out.println("Here are the tasks in your list:");
        for (Task task : tl.getTasks()) {
            System.out.println((tl.getTaskIndex(task) + 1) + "." + task);
        }
        horizontalLine();
    }

    /**
     * Prints that task is marked as completed.
     * @param t Task to be marked as completed.
     */
    public void markMessage(Task t) {
        horizontalLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t);
        horizontalLine();
    }

    /**
     * Prints that task is marked as uncompleted.
     * @param t Task to be marked as uncompleted.
     */
    public void unmarkMessage(Task t) {
        horizontalLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(t);
        horizontalLine();
    }

    /**
     * Prints that the task has been added and the number of tasks so far.
     * @param t Task to be added.
     * @param tl List of all the tasks so far.
     */
    public void taskMessage(Task t, TaskList tl) {
        horizontalLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(t);
        System.out.println("Now you have " + tl.getSize() + " tasks in the list.");
        horizontalLine();
    }

    /**
     * Prints that the task has been deleted and the number of tasks so far.
     * @param t Task to be deleted.
     * @param tl List of all the tasks so far.
     */
    public void deleteMessage(Task t, TaskList tl) {
        horizontalLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println(t);
        System.out.println("Now you have " + tl.getSize() + " tasks in the list.");
        horizontalLine();
    }
}
