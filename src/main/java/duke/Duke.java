package duke;

import duke.command.Parser;
import duke.exception.DukeException;
import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Represents the main class for the Duke application.
 * Duke is a task management application.
 */
public class Duke {
    /**
     * Entry point for starting the Duke application.
     *
     * @param args Command-line arguments passed to the program (not used).
     * @throws DukeException If an error occurs during the execution of Duke.
     */
    public static void main(String[] args) throws DukeException {
        TaskList.readTasksFromFile();
        Ui.greetUser();
        Parser.runDuke();
        Ui.sayGoodbye();
    }
}
