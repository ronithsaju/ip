import java.util.ArrayList;
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

    public void listMessage(ArrayList<Task> tasks) {
        horizontalLine();
        System.out.println("Here are the tasks in your list:");
        for (Task t : tasks) {
            System.out.println((tasks.indexOf(t) + 1) + "." + t);
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
    public void taskMessage(Task t, ArrayList<Task> tasks) {
        horizontalLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(t);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        horizontalLine();
    }

    public void deleteMessage(Task t, ArrayList<Task> tasks) {
        horizontalLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println(t);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        horizontalLine();
    }

}
