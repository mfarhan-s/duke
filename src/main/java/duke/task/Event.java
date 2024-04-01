package duke.task;

import duke.command.DateTimeParser;
import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// enter format as "event <description> /from <dd/mm/yyyy HHmm> /to <dd/mm/yyyy HHmm>"
public class Event extends Task {
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    public Event(String description, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        super(description);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    public void setFromDateTime(LocalDateTime newFromDateTime) {
        this.fromDateTime = newFromDateTime;
    }

    public void setToDateTime(LocalDateTime newToDateTime) {
        this.toDateTime = newToDateTime;
    }

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

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) +
                " to: " + toDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) + ")";
    }
}
