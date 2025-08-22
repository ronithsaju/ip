import java.util.Scanner;

public class Oreo {
    public static void horizontalLine() {
        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args) {
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
            }

            horizontalLine();
            System.out.println(userInput);
            horizontalLine();
        }
    }
}
