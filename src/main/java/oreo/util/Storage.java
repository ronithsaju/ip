package oreo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import oreo.OreoException;
import oreo.task.Deadline;
import oreo.task.Event;
import oreo.task.Task;
import oreo.task.Todo;

/**
 * Handles the loading and saving of tasks for the chatbot.
 */
public class Storage {
    private static final String DIR = "data";
    private static final String FILE_PATH = "data/oreo.txt";
    private static final DateTimeFormatter READ_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Ensures directory and text file to store tasks exists.
     */
    public Storage() throws OreoException {
        ensureFileExists();
    }

    /**
     * Creates directory and text file to save tasks to, if it does not already exist.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private void ensureFileExists() throws OreoException {
        try {
            Path dirPath = Paths.get(DIR);
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path filePath = Paths.get(FILE_PATH);
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new OreoException("Issue finding file.", e);
        }
    }

    /**
     * Runs readTasksFromFile() and catches possible exceptions thrown.
     * @return Array List of all previously saved tasks.
     * @throws OreoException Custom exception made for the chatbot.
     */
    public ArrayList<Task> loadTasks() throws OreoException {
        try {
            return readTasksFromFile();
        } catch (FileNotFoundException | OreoException e) {
            throw new OreoException("Issue reading tasks.", e);
        }
    }

    /**
     * Extracts task info from the formatted saved tasks file and loads it to the chatbot.
     * @return Array List of all previously saved tasks.
     * @throws FileNotFoundException If file does not exist.
     * @throws OreoException Custom exception made for the chatbot.
     */
    private ArrayList<Task> readTasksFromFile() throws FileNotFoundException, OreoException {
        File f = new File(FILE_PATH);
        if (!f.exists()) {
            return null; // skips everything if there is no saved tasks file
        }
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String task = s.nextLine();
            Task t;
            String[] parts = task.split("\\|");
            if (parts.length < 3) { // guard clause for invalid format
                throw new OreoException("The saved file of tasks has an invalid format :o");
            }
            String type = parts[0];
            String status = parts[1];
            String name = parts[2];
            boolean isCompleted = (status.equals("1"));

            switch (type) {
            case "T": // todo task
                t = parseTodo(parts, isCompleted, name);
                break;
            case "D": // deadline task
                t = parseDeadline(parts, isCompleted, name);
                break;
            case "E": // event task
                t = parseEvent(parts, isCompleted, name);
                break;
            default: // invalid
                throw new OreoException("The saved file of tasks has an invalid format :o");
            }
            tasks.add(t);
        }
        return tasks;
    }

    /**
     * Parses a line from the saved tasks file that represents a Todo task.
     * @param parts Split parts of the line from the saved tasks file.
     * @param isCompleted Whether the task is completed.
     * @param name Name of the todo task.
     * @return The Todo task represented by the line.
     */
    private static Task parseTodo(String[] parts, boolean isCompleted, String name) {
        Task t = new Todo(name);
        t.setIsCompleted(isCompleted);

        // check if there is a note attached to the task
        if (parts.length > 3) {
            String noteInfo = parts[3].trim();
            if (!noteInfo.isEmpty()) {
                t.setNote(noteInfo);
            }
        }
        return t;
    }

    /**
     * Parses a line from the saved tasks file that represents a Deadline task.
     * @param parts Split parts of the line from the saved tasks file.
     * @param isCompleted Whether the task is completed.
     * @param name Name of the deadline task.
     * @return The Deadline task represented by the line.
     */
    private static Task parseDeadline(String[] parts, boolean isCompleted, String name) {
        String byStr = parts[3];
        LocalDate byDateTime = LocalDate.parse(byStr, READ_DATE_FORMAT);
        Task t = new Deadline(name, byDateTime);
        t.setIsCompleted(isCompleted);

        // check if there is a note attached to the task
        if (parts.length > 4) {
            String noteInfo = parts[3].trim();
            if (!noteInfo.isEmpty()) {
                t.setNote(noteInfo);
            }
        }
        return t;
    }

    /**
     * Parses a line from the saved tasks file that represents an Event task.
     * @param parts Split parts of the line from the saved tasks file.
     * @param isCompleted Whether the task is completed.
     * @param name Name of the event task.
     * @return The Event task represented by the line.
     */
    private static Task parseEvent(String[] parts, boolean isCompleted, String name) {
        String fromStr = parts[3];
        String toStr = parts[4];
        LocalDate fromDateTime = LocalDate.parse(fromStr, READ_DATE_FORMAT);
        LocalDate toDateTime = LocalDate.parse(toStr, READ_DATE_FORMAT);
        Task t = new Event(name, fromDateTime, toDateTime);
        t.setIsCompleted(isCompleted);

        // check if there is a note attached to the task
        if (parts.length > 5) {
            String noteInfo = parts[3].trim();
            if (!noteInfo.isEmpty()) {
                t.setNote(noteInfo);
            }
        }
        return t;
    }

    /**
     * Runs writeTasksToFile(TaskList tl) and catches possible exceptions thrown.
     * @param tl List of tasks to be saved.
     * @throws OreoException Custom exception made for the chatbot.
     */
    public void saveTasks(TaskList tl) throws OreoException {
        try {
            writeTasksToFile(tl);
        } catch (IOException e) {
            throw new OreoException("Issue saving tasks.", e);
        }
    }

    /**
     * Formats and saves the tasks so far into the tasks file
     * @param tl List of all tasks so far.
     * @throws IOException If file does not exist or cannot be written into.
     */
    private void writeTasksToFile(TaskList tl) throws IOException {
        FileWriter fw = new FileWriter(FILE_PATH);
        StringBuilder textToAdd = new StringBuilder();
        for (Task t : tl.getTasks()) {
            textToAdd.append(t.saveFormat());
            textToAdd.append(System.lineSeparator());
        }
        fw.write(String.valueOf(textToAdd));
        fw.close();
    }
}
