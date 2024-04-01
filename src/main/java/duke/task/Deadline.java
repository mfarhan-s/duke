package duke.task;

import duke.command.DateTimeParser;
import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// enter format as "deadline <description> /by <dd/mm/yyyy HHmm>"
// deadline pay bills /by 21/4/2024 1200
public class Deadline extends Task {
    private LocalDateTime byDateTime;

    public Deadline(String description, LocalDateTime byDateTime) {
        super(description);
        this.byDateTime = byDateTime;
    }

    public LocalDateTime getBy() {
        return byDateTime;
    }

    public void setBy(LocalDateTime newByDateTime) {
        this.byDateTime = newByDateTime;
    }

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
                throw new DukeException("Deadline has to be in the future");
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

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) + ")";
    }
}
