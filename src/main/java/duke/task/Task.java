package duke.task;

public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    public String getDescription() {
        return description;
    }
    public boolean isDone() {
        return isDone;
    }
    public void markAsDone() {
        this.isDone = true;
    }
    public void unmarkAsDone() {
        this.isDone = false;
    }
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
    public static void echoUserCommand(Task task) {
        System.out.println("    A foolish command, mortal! You dare to utter:\n      " + task.toString());
    }
}
