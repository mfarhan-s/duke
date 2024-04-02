package duke.task;

import duke.exception.DukeException;
import duke.ui.Ui;
import duke.storage.Storage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * Manages tasks and provides methods to add, delete, mark as done, etc.
 */
public class TaskList {
    /**
     * The list of tasks.
     */
    public static ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Reads tasks from a file and loads them into the task list.
     *
     * @throws DukeException If there is an error while reading tasks from the file.
     */
    public static void readTasksFromFile() throws DukeException {
        ArrayList<Task> loadedTasks = Storage.loadTasksFromFile();
        if (!loadedTasks.isEmpty()) {
            taskList.addAll(loadedTasks);
            System.out.println("    " + taskList.size() + " task(s) loaded from previous session!");
            Ui.printHorizontalLine();
        }
    }

    /**
     * Adds a task to the task list and saves tasks to file.
     *
     * @param task The task to be added.
     * @throws DukeException If there is an error while saving tasks to file.
     */
    public static void addTask(Task task) throws DukeException {
        taskList.add(task);
        Task.echoUserCommand(task);
        System.out.println("    Now you have " + taskList.size() + " task(s) in your list.");
        Storage.saveTasksToFile(taskList);
    }

    /**
     * Displays the list of tasks.
     */
    public static void displayList() {
        if (taskList.isEmpty()) {
            System.out.println("    Your feeble Task List is Empty!");
        } else {
            System.out.println("    ======= Scroll of Puny Tasks =======");
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println("        " + (i + 1) + ". " + taskList.get(i));
            }
        }
        Ui.printHorizontalLine();
    }

    /**
     * Deletes a task from the task list.
     *
     * @param taskNumber The number of the task to be deleted.
     * @param taskList   The list of tasks.
     * @throws DukeException If there is an error while saving tasks to file.
     */
    public static void deleteTask(int taskNumber, ArrayList<Task> taskList) throws DukeException {
        if (isValidTaskNumber(taskNumber, taskList)) {
            Task deletedTask = taskList.remove(taskNumber - 1);
            Storage.saveTasksToFile(TaskList.taskList);
            System.out.println("    Witness the eradication of this feeble task:\n      " + deletedTask.toString());
            System.out.println("    Now you have " + taskList.size() + " task(s) in the list. Tremble!");
        } else {
            System.out.println("    Fool! That task number is beyond the realm of your pitiful list!");
        }
    }

    /**
     * Checks if the task number is valid.
     *
     * @param taskNumber The task number to be checked.
     * @param taskList   The list of tasks.
     * @return True if the task number is valid, false otherwise.
     */
    public static boolean isValidTaskNumber(int taskNumber, ArrayList<Task> taskList) {
        return taskNumber > 0 && taskNumber <= taskList.size();
    }

    /**
     * Marks a task as done.
     *
     * @param taskNumber The number of the task to be marked as done.
     * @throws DukeException If there is an error while saving tasks to file.
     */
    public static void markTaskAsDone(int taskNumber) throws DukeException {
        if (TaskList.isValidTaskNumber(taskNumber, TaskList.taskList)) {
            Task task = TaskList.taskList.get(taskNumber - 1);
            if (!task.isDone()) {
                task.markAsDone();
                System.out.println("    Hmph! I've smitten this task from the list:\n      " + task);
                Storage.saveTasksToFile(TaskList.taskList);
            } else {
                System.out.println("    Fool! This task has already been marked as done!\n      " + task);
            }
        } else {
            System.out.println("    Fool! That task number is beyond the realm of your pitiful list!");
        }
    }

    /**
     * Unmarks a task as done.
     *
     * @param taskNumber The number of the task to be unmarked as done.
     * @throws DukeException If there is an error while saving tasks to file.
     */
    public static void unmarkTaskAsDone(int taskNumber) throws DukeException {
        if (TaskList.isValidTaskNumber(taskNumber, TaskList.taskList)) {
            Task task = TaskList.taskList.get(taskNumber - 1);
            if (task.isDone()) {
                task.unmarkAsDone();
                System.out.println("    Bah! I've restored this task to its pathetic existence:\n      " + task);
                Storage.saveTasksToFile(TaskList.taskList);
            } else {
                System.out.println("    Fool! This task is already in its wretched, incomplete state!\n      " + task);
            }
        } else {
            System.out.println("    You dare invoke the invalid task number? Pathetic!");
        }
    }

    /**
     * Finds tasks containing a specific keyword and displays them.
     *
     * @param keyword The keyword to search for in task descriptions.
     */

    public static void findTasksByKeyword(String keyword) {
        boolean found = false;
        int count = 1;
        for (Task task : TaskList.taskList) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                if (!found) {
                    System.out.println("    Tasks containing keyword '" + keyword + "':");
                    found = true;
                }
                System.out.println("        " + count++ + ". " + task);
            }
        }
        if (!found) {
            System.out.println("    No tasks containing keyword '" + keyword + "' found.");
        }
    }

    /**
     * Postpones the due date of a task.
     *
     * @param taskNumber       The number of the task to be postponed.
     * @param newDueDateTime   The new due date and time.
     * @throws DukeException   If there is an error while saving tasks to file.
     */
    public static void postponeTask(int taskNumber, LocalDateTime newDueDateTime) throws DukeException {
        if (isValidTaskNumber(taskNumber, taskList)) {
            Task task = taskList.get(taskNumber - 1);
            if (task instanceof Deadline) {
                Deadline deadlineTask = (Deadline) task;
                deadlineTask.setBy(newDueDateTime);
                System.out.println("    Task has been postponed successfully:\n" +
                                   "   " + taskNumber + ". " + deadlineTask);
                Storage.saveTasksToFile(taskList);
            } else if (task instanceof Event) {
                Event eventTask = (Event) task;
                LocalDateTime oldStartDateTime = eventTask.getFromDateTime();
                LocalDateTime oldEndDateTime = eventTask.getToDateTime();

                // Calculate the duration between old start time and end time
                Duration duration = Duration.between(oldStartDateTime, oldEndDateTime);

                // Set new end time by adding the duration to the new start time
                LocalDateTime newEndDateTime = newDueDateTime.plus(duration);

                // Update event task with new start and end times
                eventTask.setFromDateTime(newDueDateTime);
                eventTask.setToDateTime(newEndDateTime);

                System.out.println("    Event task has been postponed successfully:\n" +
                                   "   " + taskNumber + ". " + eventTask);
                Storage.saveTasksToFile(taskList);
            }
        }
    }
}
