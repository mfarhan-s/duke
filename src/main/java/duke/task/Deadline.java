package duke.task;

import duke.command.DateTimeParser;
import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a description and a due date and time.
 */
public class Deadline extends Task {
    /** The due date and time of the deadline. */
    private LocalDateTime byDateTime;

    /**
     * Constructs a deadline task with the given description and due date and time.
     *
     * @param description The description of the deadline task.
     * @param byDateTime  The due date and time of the deadline task.
     */
    public Deadline(String description, LocalDateTime byDateTime) {
        super(description);
        this.byDateTime = byDateTime;
    }

    /**
     * Retrieves the due date and time of the deadline.
     *
     * @return The due date and time of the deadline.
     */
    public LocalDateTime getBy() {
        return byDateTime;
    }

    /**
     * Sets the due date and time of the deadline.
     *
     * @param newByDateTime The new due date and time of the deadline.
     */
    public void setBy(LocalDateTime newByDateTime) {
        this.byDateTime = newByDateTime;
    }

    /**
     * Creates a deadline task from a user command string.
     *
     * @param command The user command string for creating the deadline task.
     * @return The created deadline task, or null if an error occurs.
     */
    public static Deadline createDeadlineFromCommand(String command) {
        String prefix = "deadline";
        String byKeyword = "/by";

        int prefixIndex = command.toLowerCase().indexOf(prefix);
        int byIndex = command.indexOf(byKeyword);

        try {
            if (prefixIndex == -1 || byIndex == -1) {
                throw DukeException.invalidDeadlineFormat();
            }

            String deadlineDescription = command.substring(prefixIndex + prefix.length(), byIndex).trim();
            String byDateTimeString = command.substring(byIndex + byKeyword.length()).trim();

            LocalDateTime byDateTime = DateTimeParser.parseDateTime(byDateTimeString);

            if (byDateTime.isBefore(LocalDateTime.now())) {
                throw DukeException.invalidDateTime();
            }

            if (deadlineDescription.isEmpty() || byDateTimeString.isEmpty()) {
                throw DukeException.invalidDeadlineFormat();
            }

            return new Deadline(deadlineDescription, byDateTime);
        } catch (DukeException e) {
            DukeException.handleGracefulError(e);
            return null;
        }
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) + ")";
    }
}
