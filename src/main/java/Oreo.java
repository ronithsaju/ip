import java.util.Scanner;

public class Oreo {
    public static void horizontalLine() {
        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args) {
        int count = 1;
        String[] tasks = new String[100];
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
                for (int i = 1; i < count; i++) {
                    System.out.println(i + ". " + tasks[i]);
                }
                horizontalLine();
                continue;
            }

            tasks[count] = userInput;

            horizontalLine();
            System.out.println("added: " + userInput);
            horizontalLine();

            count++;
        }
    }
}
