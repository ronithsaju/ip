import java.util.ArrayList;
import java.util.Scanner;

public class Oreo {
    public static void horizontalLine() {
        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args){
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        horizontalLine();
        System.out.println("Hello! I'm Oreo");
        System.out.println("What can I do for you?");
        horizontalLine();

        while (true) {
            String userInput = input.nextLine();

            if (userInput.equals("bye")) {
                horizontalLine();
                System.out.println("Bye. Hope to see you again soon!");
                horizontalLine();
                break;
            } else if (userInput.equals("list")) {
                horizontalLine();
                System.out.println("Here are the tasks in your list:");
                for (Task t : tasks) {
                    System.out.println((tasks.indexOf(t) + 1) + "." + t);
                }
                horizontalLine();
            } else if (userInput.startsWith("mark")) {
                int taskNum = Integer.parseInt(
                        userInput.replaceAll("[^0-9]", "")); // removes all non-numbers from input

                Task t = tasks.get(taskNum - 1);
                t.isCompleted = true;

                horizontalLine();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(t);
                horizontalLine();
            } else if (userInput.startsWith("unmark")) {
                int taskNum = Integer.parseInt(
                        userInput.replaceAll("[^0-9]", "")); // removes all non-numbers from input

                Task t = tasks.get(taskNum - 1);
                t.isCompleted = false;

                horizontalLine();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(t);
                horizontalLine();
            } else if (userInput.startsWith("todo")) {
                Task t = new Todo(userInput.substring(5));
                tasks.add(t);

                horizontalLine();
                System.out.println("Got it. I've added this task:");
                System.out.println(t);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                horizontalLine();
            } else if (userInput.startsWith("deadline")) {
                String name = userInput.substring(9, userInput.indexOf("/by"));
                String by = userInput.substring(userInput.indexOf("/by") + 3);

                Task t = new Deadline(name, by);
                tasks.add(t);

                horizontalLine();
                System.out.println("Got it. I've added this task:");
                System.out.println(t);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                horizontalLine();
            } else if (userInput.startsWith("event")) {
                String name = userInput.substring(6, userInput.indexOf("/from"));
                String from = userInput.substring(userInput.indexOf("/from") + 5, userInput.indexOf("/to"));
                String to = userInput.substring(userInput.indexOf("/to") + 3);
                Task t = new Event(name, from, to);
                tasks.add(t);

                horizontalLine();
                System.out.println("Got it. I've added this task:");
                System.out.println(t);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                horizontalLine();
            } else {
                horizontalLine();
                System.out.println("Invalid input!!");
                System.out.println("Please write a todo, deadline or event task");
                horizontalLine();
            }
        }
    }
}
