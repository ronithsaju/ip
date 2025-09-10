package oreo.util;

import oreo.OreoException;
import oreo.task.Deadline;
import oreo.task.Event;
import oreo.task.Task;
import oreo.task.Todo;

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
            boolean isCompleted;
            String name;
            Task t;
            switch (task.charAt(0)) {
            case 'T': // todo task
                name = task.substring(4);
                t = new Todo(name);
                isCompleted = (task.charAt(2) == '1');
                t.setIsCompleted(isCompleted);
                tasks.add(t);
                break;
            case 'D': // deadline task
                name = task.substring(4, task.lastIndexOf("|"));
                String byStr = task.substring(task.lastIndexOf("|") + 1);
                LocalDate byDateTime = LocalDate.parse(byStr, READ_DATE_FORMAT);
                t = new Deadline(name, byDateTime);
                isCompleted = (task.charAt(2) == '1');
                t.setIsCompleted(isCompleted);
                tasks.add(t);
                break;
            case 'E': // event task
                name = task.substring(4, task.indexOf("|", 8));
                String fromStr = task.substring(task.indexOf("|", 8) + 1,
                        task.lastIndexOf("|"));
                String toStr = task.substring(task.lastIndexOf("|") + 1);
                LocalDate fromDateTime = LocalDate.parse(fromStr, READ_DATE_FORMAT);
                LocalDate toDateTime = LocalDate.parse(toStr, READ_DATE_FORMAT);
                t = new Event(name, fromDateTime, toDateTime);
                isCompleted = (task.charAt(2) == '1');
                t.setIsCompleted(isCompleted);
                tasks.add(t);
                break;
            default: // invalid
                throw new OreoException("The saved file of tasks has an invalid format :o");
            }
        }
        return tasks;
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
