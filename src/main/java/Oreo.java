import java.util.ArrayList;
import java.util.Scanner;

public class Oreo {
    public static void horizontalLine() {
        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args) {
        //int count = 1;
        //String[] tasks = new String[100];
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
                    System.out.println((tasks.indexOf(t) + 1) + ".[" + t.getCompletionStatusIcon() + "] " + t.name);
                }
                horizontalLine();
                continue;
            } else if (userInput.startsWith("mark")) {
                int taskNum = Integer.parseInt(
                        userInput.replaceAll("[^0-9]", "")); // removes all non-numbers from input

                Task t = tasks.get(taskNum - 1);
                t.isCompleted = true;

                horizontalLine();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("[" + t.getCompletionStatusIcon() + "] " + t.name);
                horizontalLine();
                continue;
            } else if (userInput.startsWith("unmark")) {
                int taskNum = Integer.parseInt(
                        userInput.replaceAll("[^0-9]", "")); // removes all non-numbers from input

                Task t = tasks.get(taskNum - 1);
                t.isCompleted = false;

                horizontalLine();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("[" + t.getCompletionStatusIcon() + "] " + t.name);
                horizontalLine();
                continue;
            }

            //tasks[count] = userInput;
            tasks.add(new Task(userInput));

            horizontalLine();
            System.out.println("added: " + userInput);
            horizontalLine();

            //count++;
        }
    }
}
