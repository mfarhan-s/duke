package duke.command;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeParserTest {
    @Test
    public void testParseDateTime_validDateTime_success() throws DukeException {
        String dateTimeString = "21/04/2024 1200";
        LocalDateTime expectedDateTime = LocalDateTime.of(2024, 4, 21, 12, 0);

        LocalDateTime actualDateTime = DateTimeParser.parseDateTime(dateTimeString);

        assertEquals(expectedDateTime, actualDateTime);
    }
}
