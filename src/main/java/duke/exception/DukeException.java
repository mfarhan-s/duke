package duke.exception;

public class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }

    public String getErrorMessage() {
        return "    Arise, ERROR!: " + getMessage();
    }

    public static void handleGracefulError(DukeException exception) {
        System.out.println(exception.getErrorMessage());
    }

    public static DukeException invalidTaskNumber() {
        return new DukeException("Foolish mortal! Specify a valid task number.");
    }

    public static DukeException invalidToDoFormat() {
        return new DukeException("Aha! Enter the ToDo format as follows: todo <description>");
    }

    public static DukeException invalidDeadlineFormat() {
        return new DukeException("Pathetic creature! Enter the Deadline format as follows: deadline <description> /by <dd/mm/yyyy HHmm>");
    }

    public static DukeException invalidEventFormat() {
        return new DukeException("Worthless Being! Enter the Event format as follows: event <description> /from <date> /to <date>");
    }

    public static DukeException invalidDateTimeFormat() {
        return new DukeException("You ignorant fool! Enter date and time in the correct format: dd/mm/yyyy HHmm");
    }

    public static DukeException invalidFindFormat() {
        return new DukeException("Invalid format for 'find' command. Please specify a keyword.");
    }
}
