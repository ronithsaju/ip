import java.util.ArrayList;
import java.util.Scanner;

public class Oreo {
    // basic function to print out a single horizontal line
    public static void horizontalLine() {
        System.out.println("____________________________________________________________");
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

    public static void main(String[] args) throws OreoException {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        horizontalLine();
        System.out.println("Hello! I'm Oreo");
        System.out.println("What can I do for you?");
        horizontalLine();

        while (sc.hasNextLine()) {
            String userInput = sc.nextLine();

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
                int taskNum = extractNumber(userInput);
                Task t = tasks.get(taskNum - 1);
                t.isCompleted = true;

                horizontalLine();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(t);
                horizontalLine();
            } else if (userInput.startsWith("unmark")) {
                int taskNum = extractNumber(userInput);
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
                try {
                    String name = userInput.substring(9, userInput.indexOf("/by"));
                    String by = userInput.substring(userInput.indexOf("/by") + 3);
                    Task t = new Deadline(name, by);
                    tasks.add(t);

                    horizontalLine();
                    System.out.println("Got it. I've added this task:");
                    System.out.println(t);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    horizontalLine();
                } catch (Exception e) {
                    throw new OreoException("No name or by date provided for deadline :(");
                }
            } else if (userInput.startsWith("event")) {
                try {
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
                } catch (Exception e) {
                    throw new OreoException("No name or from/to date provided for event :(");
                }
            } else if (userInput.startsWith("delete")) {
                int taskNum = extractNumber(userInput);
                Task t = tasks.get(taskNum - 1);
                tasks.remove(t);

                horizontalLine();
                System.out.println("Noted. I've removed this task:");
                System.out.println(t);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                horizontalLine();
            } else {
                horizontalLine();
                throw new OreoException("Invalid input! Please write a todo, deadline or event task");
            }
        }
    }
}
