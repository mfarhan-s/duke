package duke.task;

import duke.command.DateTimeParser;
import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// enter format as "event <description> /from <dd/mm/yyyy HHmm> /to <dd/mm/yyyy HHmm>"
/**
 * Represents an event task with a description and a duration.
 */
public class Event extends Task {
    /** The start date and time of the event. */
    private LocalDateTime fromDateTime;
    /** The end date and time of the event. */
    private LocalDateTime toDateTime;

    /**
     * Constructs an event task with the given description, start date and time, and end date and time.
     *
     * @param description   The description of the event task.
     * @param fromDateTime  The start date and time of the event.
     * @param toDateTime    The end date and time of the event.
     */
    public Event(String description, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        super(description);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    /**
     * Retrieves the start date and time of the event.
     *
     * @return The start date and time of the event.
     */
    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    /**
     * Retrieves the end date and time of the event.
     *
     * @return The end date and time of the event.
     */
    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    /**
     * Sets the start date and time of the event.
     *
     * @param newFromDateTime The new start date and time of the event.
     */
    public void setFromDateTime(LocalDateTime newFromDateTime) {
        this.fromDateTime = newFromDateTime;
    }

    /**
     * Sets the end date and time of the event.
     *
     * @param newToDateTime The new end date and time of the event.
     */
    public void setToDateTime(LocalDateTime newToDateTime) {
        this.toDateTime = newToDateTime;
    }

    /**
     * Creates an event task from a user command string.
     *
     * @param command The user command string for creating the event task.
     * @return The created event task, or null if an error occurs.
     */
    public static Event createEventFromCommand(String command) {
        String prefix = "event";
        String fromKeyword = "/from";
        String toKeyword = "/to";

        int prefixIndex = command.toLowerCase().indexOf(prefix);
        int fromIndex = command.indexOf(fromKeyword);
        int toIndex = command.indexOf(toKeyword);

        try {
            if (prefixIndex == -1 || fromIndex == -1 || toIndex == -1) {
                throw DukeException.invalidEventFormat();
            }

            String eventDescription = command.substring(prefixIndex + prefix.length(), fromIndex).trim();
            String fromDateTimeString = command.substring(fromIndex + fromKeyword.length(), toIndex).trim();
            String toDateTimeString = command.substring(toIndex + toKeyword.length()).trim();

            if (eventDescription.isEmpty() || fromDateTimeString.isEmpty() || toDateTimeString.isEmpty()) {
                throw DukeException.invalidEventFormat();
            }

            LocalDateTime fromDateTime = DateTimeParser.parseDateTime(fromDateTimeString);
            LocalDateTime toDateTime = DateTimeParser.parseDateTime(toDateTimeString);

            if (fromDateTime.isAfter(toDateTime)) {
                throw new DukeException("Start time cannot be after end time");
            }

            if (fromDateTime.isBefore(LocalDateTime.now())) {
                throw new DukeException("Start time must be in the future");
            }

            return new Event(eventDescription, fromDateTime, toDateTime);
        } catch (DukeException e) {
            DukeException.handleGracefulError(e);
            return null;
        }
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return A string representation of the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) +
                " to: " + toDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) + ")";
    }
}
