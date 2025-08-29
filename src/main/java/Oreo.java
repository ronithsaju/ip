import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Oreo {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter READ_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    public static void loadTasks(ArrayList<Task> tasks) {
        try {
            readTasksFromFile(tasks);
        } catch (FileNotFoundException | OreoException e) {
            System.out.println("Issue reading tasks: " + e.getMessage());
        }
    }

    // extracts info from the formatted saved tasks file and loads it
    public static void readTasksFromFile(ArrayList<Task> tasks) throws FileNotFoundException, OreoException {
        File f = new File("./data/oreo.txt");
        if (!f.exists()) return; // skips if there is no saved tasks file
        Scanner s = new Scanner(f);
        while (s.hasNext())  {
            String task = s.nextLine();
            boolean isCompleted;
            String name;
            Task t;
            switch (task.charAt(0)) {
                case 'T':
                    name = task.substring(4);
                    t = new Todo(name);
                    isCompleted = (task.charAt(2) == '1');
                    t.isCompleted = isCompleted;
                    tasks.add(t);
                    break;
                case 'D':
                    name = task.substring(4,  task.lastIndexOf("|"));
                    String byStr = task.substring(task.lastIndexOf("|") + 1);
                    LocalDate byDateTime = LocalDate.parse(byStr, READ_DATE_FORMAT);
                    t = new Deadline(name, byDateTime);
                    isCompleted = (task.charAt(2) == '1');
                    t.isCompleted = isCompleted;
                    tasks.add(t);
                    break;
                case 'E':
                    name = task.substring(4,  task.indexOf("|", 8));
                    String fromStr = task.substring(task.indexOf("|", 8) + 1, task.lastIndexOf("|"));
                    String toStr = task.substring(task.lastIndexOf("|") + 1);
                    LocalDate fromDateTime = LocalDate.parse(fromStr, READ_DATE_FORMAT);
                    LocalDate toDateTime = LocalDate.parse(toStr, READ_DATE_FORMAT);
                    t = new Event(name, fromDateTime, toDateTime);
                    isCompleted = (task.charAt(2) == '1');
                    t.isCompleted = isCompleted;
                    tasks.add(t);
                    break;
                default:
                    throw new OreoException("The saved file of tasks has an invalid format :o");
            }
        }
    }

    public static void saveTasks(ArrayList<Task> updated_tasks) {
        try {
            writeTasksToFile(updated_tasks);
        } catch (IOException e) {
            System.out.println("Issue saving tasks: " + e.getMessage());
        }
    }

    // formats and saves the tasks so far
    public static void writeTasksToFile(ArrayList<Task> updated_tasks) throws IOException {
        FileWriter fw = new FileWriter("./data/oreo.txt");
        StringBuilder textToAdd = new StringBuilder();
        for (Task t : updated_tasks) {
            textToAdd.append(t.saveFormat());
            textToAdd.append(System.lineSeparator());
        }
        fw.write(String.valueOf(textToAdd));
        fw.close();
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasks(tasks);
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
                saveTasks(tasks);

                horizontalLine();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(t);
                horizontalLine();
            } else if (userInput.startsWith("unmark")) {
                int taskNum = extractNumber(userInput);
                Task t = tasks.get(taskNum - 1);
                t.isCompleted = false;
                saveTasks(tasks);

                horizontalLine();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(t);
                horizontalLine();
            } else if (userInput.startsWith("todo")) {
                Task t = new Todo(userInput.substring(5));
                tasks.add(t);
                saveTasks(tasks);

                horizontalLine();
                System.out.println("Got it. I've added this task:");
                System.out.println(t);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                horizontalLine();
            } else if (userInput.startsWith("deadline")) {
                try {
                    String name = userInput.substring(9, userInput.indexOf("/by") -  1);
                    String byStr = userInput.substring(userInput.indexOf("/by") + 4);

                    try {
                        LocalDate byDateTime = LocalDate.parse(byStr, DATE_FORMAT);
                        Task t = new Deadline(name, byDateTime);
                        tasks.add(t);
                        saveTasks(tasks);

                        horizontalLine();
                        System.out.println("Got it. I've added this task:");
                        System.out.println(t);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        horizontalLine();
                    } catch (DateTimeParseException e) {
                        throw new OreoException("Invalid date format. " +
                                "Please follow DD/MM/YYYY. e.g. 17/11/2002");
                    }
                } catch (Exception ex) {
                    throw new Exception("Failed to create deadline task", ex);
                }
            } else if (userInput.startsWith("event")) {
                try {
                    String name = userInput.substring(6, userInput.indexOf("/from") - 1);
                    String fromStr = userInput.substring(userInput.indexOf("/from") + 6, userInput.indexOf("/to") - 1);
                    String toStr = userInput.substring(userInput.indexOf("/to") + 4);

                    try {
                        LocalDate fromDateTime = LocalDate.parse(fromStr, DATE_FORMAT);
                        LocalDate toDateTime = LocalDate.parse(toStr, DATE_FORMAT);
                        Task t = new Event(name, fromDateTime, toDateTime);
                        tasks.add(t);
                        saveTasks(tasks);

                        horizontalLine();
                        System.out.println("Got it. I've added this task:");
                        System.out.println(t);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        horizontalLine();
                    } catch (DateTimeParseException e) {
                        throw new OreoException("Invalid date format. " +
                                "Please follow DD/MM/YYYY. e.g. 17/11/2002");
                    }
                } catch (Exception ex) {
                    throw new Exception("Failed to create deadline task", ex);
                }
            } else if (userInput.startsWith("delete")) {
                int taskNum = extractNumber(userInput);
                Task t = tasks.get(taskNum - 1);
                tasks.remove(t);
                saveTasks(tasks);

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
