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
     * The main method to start the Duke application.
     *
     * @param args The command-line arguments passed to the program.
     * @throws DukeException If there is an error while running Duke.
     */
    public static void main(String[] args) throws DukeException {
        TaskList.readTasksFromFile();
        Ui.greetUser();
        Parser.runDuke();
        Ui.sayGoodbye();
    }
}
