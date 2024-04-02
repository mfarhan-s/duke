package duke.command;

import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing date and time strings.
 */
public class DateTimeParser {
    /**
     * Parses a date and time string into a LocalDateTime object.
     *
     * @param dateTimeString The string representing date and time.
     * @return The parsed LocalDateTime object.
     * @throws DukeException If the date and time string is in an invalid format.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DukeException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw DukeException.invalidDateTimeFormat();
        }
    }
}
