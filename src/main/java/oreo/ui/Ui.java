package oreo.ui;

import oreo.task.Task;
import oreo.util.TaskList;

import java.util.Scanner;

public class Ui {
    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public String readCommand() {
        return sc.nextLine();
    }

    // basic function to print out a single horizontal line
    public void horizontalLine() {
        System.out.println("____________________________________________________________");
    }

    public void welcomeMessage() {
        horizontalLine();
        System.out.println("Hello! I'm Oreo");
        System.out.println("What can I do for you?");
        horizontalLine();
    }

    public void byeMessage() {
        horizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        horizontalLine();
    }

    public void listMessage(TaskList tl) {
        horizontalLine();
        System.out.println("Here are the tasks in your list:");
        for (Task task : tl.getTasks()) {
            System.out.println((tl.getTaskIndex(task) + 1) + "." + task);
        }
        horizontalLine();
    }

    public void markMessage(Task t) {
        horizontalLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t);
        horizontalLine();
    }

    public void unmarkMessage(Task t) {
        horizontalLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(t);
        horizontalLine();
    }

    // used for todo, deadline and event tasks
    public void taskMessage(Task t, TaskList tl) {
        horizontalLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(t);
        System.out.println("Now you have " + tl.getSize() + " tasks in the list.");
        horizontalLine();
    }

    public void deleteMessage(Task t, TaskList tl) {
        horizontalLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println(t);
        System.out.println("Now you have " + tl.getSize() + " tasks in the list.");
        horizontalLine();
    }

    public void findMessage(TaskList tl) {
        horizontalLine();
        System.out.println("Here are the matching tasks in your list:");
        for (Task task : tl.getTasks()) {
            System.out.println((tl.getTaskIndex(task) + 1) + "." + task);
        }
        horizontalLine();
    }
}
