package duke.command;

import java.time.LocalDateTime;
import java.util.Scanner;

import duke.task.*;
import duke.ui.Ui;
import duke.exception.DukeException;

/**
 * Represents a parser for user commands.
 * Parses user commands and executes corresponding actions.
 */
public class Parser {
    /**
     * Executes the provided command.
     *
     * @param command The command to execute.
     */
    public static void executeCommand(String command) {
        try {
            String[] commandParts = command.split(" ", 3);
            String commandType = commandParts[0].toLowerCase();

            switch (commandType) {
                case "list":
                    executeListCommand();
                    break;
                case "mark":
                    executeMarkCommand(commandParts);
                    break;
                case "unmark":
                    executeUnmarkCommand(commandParts);
                    break;
                case "todo":
                    executeTodoCommand(command, commandParts);
                    break;
                case "deadline":
                    executeDeadlineCommand(command, commandParts);
                    break;
                case "event":
                    executeEventCommand(command, commandParts);
                    break;
                case "delete":
                    executeDeleteCommand(commandParts);
                    break;
                case "find":
                    executeFindCommand(commandParts);
                    break;
                case "postpone":
                    executePostponeCommand(commandParts);
                    break;
                default:
                    Ui.displayMessage("    Unknown command! Enter a valid command.");
            }

        } catch (DukeException e) {
            Ui.displayMessage(e.getMessage());
        }
    }

    /**
     * Executes the "list" command.
     *
     * @throws DukeException If an error occurs during execution.
     */
    private static void executeListCommand() throws DukeException {
        Ui.displayTaskList(TaskList.taskList);
    }

    /**
     * Executes the "mark" command.
     *
     * @param commandParts The command parts.
     * @throws DukeException If an error occurs during execution.
     */
    private static void executeMarkCommand(String[] commandParts) throws DukeException {
        if (commandParts.length > 1) {
            try {
                TaskList.markTaskAsDone(Integer.parseInt(commandParts[1]));
            } catch (NumberFormatException e) {
                DukeException.handleGracefulError(DukeException.invalidTaskNumber());
            }
        } else {
            DukeException.handleGracefulError(DukeException.invalidTaskNumber());
        }
    }

    /**
     * Executes the "unmark" command.
     *
     * @param commandParts The command parts.
     * @throws DukeException If an error occurs during execution.
     */
    private static void executeUnmarkCommand(String[] commandParts) throws DukeException {
        if (commandParts.length > 1) {
            try {
                TaskList.unmarkTaskAsDone(Integer.parseInt(commandParts[1]));
            } catch (NumberFormatException e) {
                DukeException.handleGracefulError(DukeException.invalidTaskNumber());
            }
        } else {
            DukeException.handleGracefulError(DukeException.invalidTaskNumber());
        }
    }

    /**
     * Executes the "todo" command.
     *
     * @param command      The full command string.
     * @param commandParts The command parts.
     * @throws DukeException If an error occurs during execution.
     */
    private static void executeTodoCommand(String command, String[] commandParts) throws DukeException {
        if (commandParts.length > 1) {
            ToDo toDoTask = ToDo.createToDoFromCommand(command);
            if (toDoTask != null) {
                TaskList.addTask(toDoTask);
            } else {
                DukeException.handleGracefulError(DukeException.invalidToDoFormat());
            }
        } else {
            DukeException.handleGracefulError(DukeException.invalidToDoFormat());
        }
    }

    /**
     * Executes the "deadline" command.
     *
     * @param command      The full command string.
     * @param commandParts The command parts.
     * @throws DukeException If an error occurs during execution.
     */
    private static void executeDeadlineCommand(String command, String[] commandParts) throws DukeException {
        if (commandParts.length > 1) {
            Deadline deadlineTask = Deadline.createDeadlineFromCommand(command);
            if (deadlineTask != null) {
                TaskList.addTask(deadlineTask);
            } else {
                DukeException.handleGracefulError(DukeException.invalidDeadlineFormat());
            }
        } else {
            DukeException.handleGracefulError(DukeException.invalidDeadlineFormat());
        }
    }

    /**
     * Executes the "event" command.
     *
     * @param command      The full command string.
     * @param commandParts The command parts.
     * @throws DukeException If an error occurs during execution.
     */
    private static void executeEventCommand(String command, String[] commandParts) throws DukeException {
        if (commandParts.length > 1) {
            Event eventTask = Event.createEventFromCommand(command);
            if (eventTask != null) {
                TaskList.addTask(eventTask);
            } else {
                DukeException.handleGracefulError(DukeException.invalidEventFormat());
            }
        } else {
            DukeException.handleGracefulError(DukeException.invalidEventFormat());
        }
    }

    /**
     * Executes the "delete" command.
     *
     * @param commandParts The command parts.
     * @throws DukeException If an error occurs during execution.
     */
    private static void executeDeleteCommand(String[] commandParts) throws DukeException {
        if (commandParts.length > 1) {
            try {
                int taskNumber = Integer.parseInt(commandParts[1]);
                TaskList.deleteTask(taskNumber, TaskList.taskList);
            } catch (NumberFormatException e) {
                DukeException.handleGracefulError(DukeException.invalidTaskNumber());
            }
        } else {
            DukeException.handleGracefulError(DukeException.invalidTaskNumber());
        }
    }

    /**
     * Executes the "find" command.
     *
     * @param commandParts The command parts.
     * @throws DukeException If an error occurs during execution.
     */
    private static void executeFindCommand(String[] commandParts) throws DukeException {
        if (commandParts.length > 1) {
            String keyword = commandParts[1].trim();
            TaskList.findTasksByKeyword(keyword);
        } else {
            DukeException.handleGracefulError(DukeException.invalidFindFormat());
        }
    }

    /**
     * Executes the "postpone" command.
     *
     * @param commandParts The command parts.
     * @throws DukeException If an error occurs during execution.
     */
    private static void executePostponeCommand(String[] commandParts) throws DukeException {
        if (commandParts.length > 2) {
            try {
                int taskNumber = Integer.parseInt(commandParts[1]);
                if (!TaskList.isValidTaskNumber(taskNumber, TaskList.taskList)) {
                    DukeException.handleGracefulError(DukeException.invalidTaskNumber());
                }
                LocalDateTime newDueDateTime = DateTimeParser.parseDateTime(commandParts[2]);
                if (newDueDateTime.isBefore(LocalDateTime.now())) {
                    DukeException.handleGracefulError(DukeException.invalidDateTimeFormat());
                } else {
                    TaskList.postponeTask(taskNumber, newDueDateTime);
                }
            } catch (NumberFormatException e) {
                DukeException.handleGracefulError(DukeException.invalidTaskNumber());
            } catch (DukeException e) {
                throw new DukeException("Error: " + e.getMessage());
            }
        } else {
            DukeException.handleGracefulError(DukeException.invalidPostponeFormat());
        }
    }

    /**
     * Runs the Duke application by taking user input and executing commands until the user exits.
     */
    public void runDuke() {
        String userInput;
        Scanner in = new Scanner(System.in);

        do {
            userInput = in.nextLine().trim();
            if (userInput.isEmpty()) {
                System.out.println(new DukeException("Enter a valid command").getErrorMessage());
            }
            if (userInput.equalsIgnoreCase("bye")) {
                continue;
            } else if (userInput.equalsIgnoreCase("list")) {
                TaskList.displayList();
                continue;
            }
            Parser.executeCommand(userInput);
            Ui.printHorizontalLine();
        } while (!userInput.equalsIgnoreCase("bye"));
    }
}
