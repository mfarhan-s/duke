package duke.ui;

import java.util.ArrayList;

import duke.exception.DukeException;
import duke.task.Task;
import duke.task.TaskList;
import duke.storage.Storage;

/**
 * Handles user interface interactions, including displaying messages and task lists.
 */
public class Ui {
    /** A horizontal line separator used for formatting messages. */
    private static final String HORIZONTAL_LINE = "    -------------------------------------------------";

    /**
     * Greets the user with a welcoming message.
     */
    public static void greetUser() {
        System.out.println("    Greetings, mortal! I am Balrog, the fiery demon.");
        System.out.println("    What foolish commands do you wish to utter?");
        printHorizontalLine();
    }

    /**
     * Bids farewell to the user with a goodbye message and saves tasks to file.
     *
     * @throws DukeException If there is an error while saving tasks to file.
     */
    public static void sayGoodbye() throws DukeException {
        Storage.saveTasksToFile(TaskList.taskList);
        displayMessage("    Flee, mortal! Until our paths cross again!");
        printHorizontalLine();
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    public static void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays the task list to the user.
     *
     * @param taskList The list of tasks to be displayed.
     */
    public static void displayTaskList(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            displayMessage("    Your feeble Task List is Empty!");
        } else {
            displayMessage("    ======= Scroll of Puny Tasks =======");
            for (int i = 0; i < taskList.size(); i++) {
                displayMessage("        " + (i + 1) + ". " + taskList.get(i));
            }
        }
        printHorizontalLine();
    }

    /**
     * Prints a horizontal line separator to the console.
     */
    public static void printHorizontalLine() {
        displayMessage(HORIZONTAL_LINE);
    }
}
