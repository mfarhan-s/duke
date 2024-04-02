package duke.exception;

/**
 * Represents an exception specific to the Duke application.
 * This class extends the general Exception class.
 */
public class DukeException extends Exception {
    /**
     * Constructs a DukeException with the specified detail message.
     *
     * @param message The detail message.
     */
    public DukeException(String message) {
        super(message);
    }

    /**
     * Returns a formatted error message for displaying to the user.
     *
     * @return The formatted error message.
     */
    public String getErrorMessage() {
        return "    Arise, ERROR!: " + getMessage();
    }

    /**
     * Handles the given DukeException gracefully by printing its error message.
     *
     * @param exception The DukeException to handle.
     */
    public static void handleGracefulError(DukeException exception) {
        System.out.println(exception.getErrorMessage());
    }

    /**
     * Creates a DukeException for an invalid task number.
     *
     * @return The DukeException for an invalid task number.
     */
    public static DukeException invalidTaskNumber() {
        return new DukeException("Foolish mortal! Specify a valid task number.");
    }

    /**
     * Creates a DukeException for an invalid ToDo format.
     *
     * @return The DukeException for an invalid ToDo format.
     */
    public static DukeException invalidToDoFormat() {
        return new DukeException("Aha! Enter the ToDo format as follows: todo <description>");
    }

    /**
     * Creates a DukeException for an invalid Deadline format.
     *
     * @return The DukeException for an invalid Deadline format.
     */
    public static DukeException invalidDeadlineFormat() {
        return new DukeException("Pathetic creature! Enter the Deadline format as follows: deadline <description> /by <dd/mm/yyyy HHmm>");
    }

    /**
     * Creates a DukeException for an invalid Event format.
     *
     * @return The DukeException for an invalid Event format.
     */
    public static DukeException invalidEventFormat() {
        return new DukeException("Worthless Being! Enter the Event format as follows: event <description> /from <date> /to <date>");
    }

    /**
     * Creates a DukeException for an invalid date-time format.
     *
     * @return The DukeException for an invalid date-time format.
     */
    public static DukeException invalidDateTimeFormat() {
        return new DukeException("You ignorant fool! Enter date and time in the correct format: dd/mm/yyyy HHmm");
    }

    /**
     * Creates a DukeException for an invalid 'find' command format.
     *
     * @return The DukeException for an invalid 'find' command format.
     */
    public static DukeException invalidFindFormat() {
        return new DukeException("Invalid format for 'find' command. Please specify a keyword.");
    }

    /**
     * Creates a DukeException for an invalid 'postpone' format.
     *
     * @return The DukeException for an invalid 'postpone' format.
     */
    public static DukeException invalidSnoozeFormat() {
        return new DukeException("Invalid 'postpone' format");
    }
}
