package duke.data.exception;

/**
 * Represents the various types of exceptions that appear in the program.
 * It contains the error message for each type.
 */
public enum DukeExceptionType {
    DEADLINE_NO_DESCRIPTION(
"The description of a deadline cannot be empty.\n" +
        "Correct usage: deadline <description> /by <date>"
    ),
    EVENT_NO_DESCRIPTION(
"The description of an event cannot be empty.\n" +
        "Correct usage: event <description> /from <start> /to <end>"
    ),
    TODO_NO_DESCRIPTION(
"The description of a todo cannot be empty.\n" +
        "Correct usage: todo <description>"
    ),
    DUE_NO_DATE(
"Invalid\n" +
        "Correct usage: due <dd-mm-yyyy>"
    ),
    INVALID_DEADLINE_FORMAT(
"Invalid deadline format!\n" +
        "Correct usage: deadline <description> /by <dd-mm-yyyy hh:mm>"
    ),
    INVALID_EVENT_FORMAT(
"Invalid event format!\n" +
        "Correct usage: event <description> /from <dd-mm-yyyy hh:mm> /to <dd-mm-yyyy hh:mm>"
    ),
    INVALID_EVENT_ARGUMENT(
"Invalid Argument: /from date time cannot be after /to date time"
    ),
    INVALID_RANGE("The task number you've entered is out of the valid range."),
    NO_TASK_NUMBER("Please enter the task number."),
    TASK_ALREADY_MARKED("The task number you've entered is already marked as complete."),
    TASK_ALREADY_UNMARKED("The task number you've entered is already unmarked."),
    UNKNOWN_COMMAND("I'm sorry, I don't know what that means :-(");

    private final String message;

    /**
     * Initializes a type of exception that can occur in the program.
     *
     * @param message Error message
     */
    DukeExceptionType(String message) {
        this.message = message;
    }

    /**
     * Gets the error message
     *
     * @return Error message
     */
    public String getMessage() {
        return message;
    }
}
