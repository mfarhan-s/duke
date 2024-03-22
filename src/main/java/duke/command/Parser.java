package duke.command;

import java.util.Scanner;
import duke.task.*;
import duke.ui.Ui;
import duke.exception.DukeException;
public class Parser {
    public static void executeCommand(String command) throws DukeException {
        String[] commandParts = command.split(" ", 2);

        switch (commandParts[0].toLowerCase()) {
            case "list":
                Ui.displayTaskList(TaskList.taskList);
                break;
            case "mark":
                if (commandParts.length > 1) {
                    try {
                        TaskList.markTaskAsDone(Integer.parseInt(commandParts[1]));
                    } catch (NumberFormatException e) {
                        DukeException.handleGracefulError(DukeException.invalidTaskNumber());
                    } catch (DukeException e) {
                        throw new DukeException("Error: " + e.getMessage());
                    }
                } else {
                    DukeException.handleGracefulError(DukeException.invalidTaskNumber());
                }
                break;
            case "unmark":
                if (commandParts.length > 1) {
                    try {
                        TaskList.unmarkTaskAsDone(Integer.parseInt(commandParts[1]));
                    } catch (NumberFormatException e) {
                        DukeException.handleGracefulError(DukeException.invalidTaskNumber());
                    }
                } else {
                    DukeException.handleGracefulError(DukeException.invalidTaskNumber());
                }
                break;
            case "todo":
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
                break;
            case "deadline":
                if (commandParts.length > 1) {
                    Deadline deadlineTask = Deadline.createDeadlineFromCommand(command);
                    if (deadlineTask != null) {
                        TaskList.addTask(deadlineTask);
                    } else {
                        DukeException.handleGracefulError(DukeException.invalidDeadlineFormat());
                    }
                }
                else {
                    DukeException.handleGracefulError(DukeException.invalidDeadlineFormat());
                }
                break;
            case "event":
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
                break;
            case "delete":
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
                break;
            default:
                TaskList.addTask(new Task(command));
        }
    }
    public static void runDuke() throws DukeException {
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
