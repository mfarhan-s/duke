package duke;

import duke.command.Parser;
import duke.exception.DukeException;
import duke.ui.Ui;
import duke.task.TaskList;

public class Duke {

    public static void main(String[] args) throws DukeException {
        Parser parser = new Parser();
        Ui ui = new Ui();
        TaskList taskList = new TaskList();

        taskList.readTasksFromFile();
        ui.greetUser();
        parser.runDuke();
        ui.sayGoodbye();
    }
}
