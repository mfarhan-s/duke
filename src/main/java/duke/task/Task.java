package duke.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    /** The description of the task. */
    private String description;
    /** The completion status of the task. */
    private boolean isDone;

    /**
     * Constructs a task with the given description and sets its completion status to false.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Retrieves the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the task is done.
     *
     * @return True if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Retrieves the status icon of the task.
     *
     * @return The status icon of the task (X if done, empty string if not done).
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A string representation of the task.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Echoes the user command for the task.
     *
     * @param task The task whose user command is echoed.
     */
    public static void echoUserCommand(Task task) {
        System.out.println("    A foolish command, mortal! You dare to utter:\n        " + TaskList.taskList.size() + ". " + task.toString());
    }
}
