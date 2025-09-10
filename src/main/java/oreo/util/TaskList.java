package oreo.util;

import java.util.ArrayList;

import oreo.task.Task;

/**
 * Represents a list of tasks and provides methods to create, read, update and delete tasks inside it.
 * Uses Java ArrayList class.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task t) {
        tasks.add(t);
    }

    public void removeTask(Task t) {
        tasks.remove(t);
    }

    public Task getTask(int x) {
        return tasks.get(x);
    }

    public int getTaskIndex(Task t) {
        return tasks.indexOf(t);
    }

    public int getSize() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns a list of tasks with names that matches the provided keyword.
     * @param keyword Name of task the user is trying to find.
     * @return A list of tasks with matching names.
     */
    public TaskList findTaskByKeywordSearch(String keyword) {
        ArrayList<Task> tasksFound = new ArrayList<>();
        for (Task t : tasks) {
            String taskName = t.getName();
            if (taskName.contains(keyword)) {
                tasksFound.add(t);
            }
        }
        return new TaskList(tasksFound);
    }
}
