package duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void testCreateTodo(){
        String line = "todo read book";

        assertEquals("[T][ ] read book", duke.task.ToDo.createToDoFromCommand(line).toString());
    }
}
