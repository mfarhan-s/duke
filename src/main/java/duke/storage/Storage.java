package duke.storage;

import duke.command.DateTimeParser;
import duke.task.*;
import duke.exception.DukeException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.io.IOException;

/**
 * Represents the storage utility for saving and loading tasks.
 * Manages the reading and writing of tasks to a file.
 */
public class Storage {
    /**
     * The file path for storing tasks.
     */
    private static final String FILE_PATH = "./data/duke.txt";

    /**
     * Saves the list of tasks to a file.
     *
     * @param taskList The list of tasks to save.
     * @throws DukeException If there is an error while saving tasks to the file.
     */
    public static void saveTasksToFile(ArrayList<Task> taskList) throws DukeException {
        try {
            Path filePath = Paths.get(FILE_PATH);
            createDirectoriesIfNeeded(filePath);
            List<String> lines = new ArrayList<>();
            for (Task task : taskList) {
                lines.add(taskToFileString(task));
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new DukeException("Error saving tasks to file: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from a file into a list of tasks.
     *
     * @return The list of tasks loaded from the file.
     * @throws DukeException If there is an error while loading tasks from the file.
     */
    public static ArrayList<Task> loadTasksFromFile() throws DukeException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        try {
            Path filePath = Paths.get(FILE_PATH);
            createDirectoriesIfNeeded(filePath);
            createFileIfNeeded(filePath);
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                Task task = fileStringToTask(line);
                if (task != null) {
                    loadedTasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new DukeException("Error loading tasks from file: " + e.getMessage());
        }
        return loadedTasks;
    }

    /**
     * Creates necessary directories if they do not exist.
     *
     * @param filePath The file path.
     * @throws DukeException If there is an error while creating directories.
     */
    private static void createDirectoriesIfNeeded(Path filePath) throws DukeException {
        Path directoryPath = filePath.getParent();
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new DukeException("Error creating directory: " + e.getMessage());
            }
        }
    }

    /**
     * Creates the file if it does not exist.
     *
     * @param filePath The file path.
     * @throws DukeException If there is an error while creating the file.
     */
    private static void createFileIfNeeded(Path filePath) throws DukeException {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new DukeException("Error creating file: " + e.getMessage());
            }
        }
    }

    /**
     * Converts a task object to a string representation for storing in the file.
     *
     * @param task The task object.
     * @return The string representation of the task for storing in the file.
     * @throws DukeException If there is an error while converting the task to string.
     */
    private static String taskToFileString(Task task) throws DukeException {
        if (task instanceof ToDo) {
            return String.format("T | %d | %s", task.isDone() ? 1 : 0, task.getDescription());
        } else if (task instanceof Deadline) {
            Deadline deadlineTask = (Deadline) task;
            String formattedDate = deadlineTask.getBy().format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
            return String.format("D | %d | %s | %s", task.isDone() ? 1 : 0, task.getDescription(), formattedDate);
        } else if (task instanceof Event) {
            Event eventTask = (Event) task;
            String formattedFrom = eventTask.getFromDateTime().format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
            String formattedTo = eventTask.getToDateTime().format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
            return String.format("E | %d | %s | %s - %s", task.isDone() ? 1 : 0, task.getDescription(), formattedFrom, formattedTo);
        } else {
            throw new DukeException("Error formatting task to string: Unknown task type.");
        }
    }

    /**
     * Converts a string representation from the file to a task object.
     *
     * @param fileString The string representation from the file.
     * @return The task object created from the string representation.
     * @throws DukeException If there is an error while converting the string to task.
     */
    private static Task fileStringToTask(String fileString) throws DukeException {
        String[] fields = fileString.split(" \\| ");
        if (fields.length < 3) {
            throw new DukeException("Invalid task format: " + fileString);
        }

        char taskType = fields[0].charAt(0);
        boolean isDone = fields[1].equals("1");
        String description = fields[2];

        switch (taskType) {
            case 'T':
                ToDo todo = new ToDo(description);
                if (isDone) {
                    todo.markAsDone();
                }
                return todo;
            case 'D':
                if (fields.length < 4) {
                    System.err.println("Invalid deadline format: " + fileString);
                    return null;
                }
                String byDateTimeString = fields[3];
                LocalDateTime byDateTime = DateTimeParser.parseDateTime(byDateTimeString);
                Deadline deadline = new Deadline(description, byDateTime);
                if (isDone) {
                    deadline.markAsDone();
                }
                return deadline;
            case 'E':
                if (fields.length < 4) {
                    System.err.println("Invalid event format: " + fileString);
                    return null;
                }
                String[] fromTo = fields[3].split(" - ");
                if (fromTo.length != 2) {
                    System.err.println("Invalid event format: " + fileString);
                    return null;
                }
                String fromDateTimeString = fromTo[0];
                String toDateTimeString = fromTo[1];
                LocalDateTime fromDateTime = DateTimeParser.parseDateTime(fromDateTimeString);
                LocalDateTime toDateTime = DateTimeParser.parseDateTime(toDateTimeString);
                Event event = new Event(description, fromDateTime, toDateTime);

                if (isDone) {
                    event.markAsDone();
                }
                return event;
            default:
                throw new DukeException("Unknown task type: " + taskType);
        }
    }
}
