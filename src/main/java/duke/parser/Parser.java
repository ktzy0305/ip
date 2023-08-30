package duke.parser;

import duke.commands.*;

import duke.data.exception.DukeException;
import duke.data.exception.DukeExceptionType;
import duke.data.task.Deadline;
import duke.data.task.Event;
import duke.data.task.ToDo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static final Pattern COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    public static final Pattern TASK_NUMBER_ARGS_FORMAT = Pattern.compile("(?<taskNumber>.+)");
    public static final Pattern DEADLINE_ARGS_FORMAT =
            Pattern.compile("(?<description>.*?)\\s+/by\\s+(?<deadline>.*)");
    public static final Pattern EVENT_ARGS_FORMAT =
            Pattern.compile("(?<description>.*?)\\s+/from\\s+(?<fromDate>.*?)\\s+/to\\s+(?<toDate>.*)");

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Command parse(String userInput) throws DukeException {
        Matcher matcher = COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new DukeException(DukeExceptionType.UNKNOWN_COMMAND);
        }

        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        switch (commandWord) {
        case ByeCommand.COMMAND_WORD:
            return new ByeCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case MarkCommand.COMMAND_WORD:
            return prepareMarkCommand(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return prepareUnmarkCommand(arguments);

        case AddDeadlineCommand.COMMAND_WORD:
            return prepareAddDeadlineCommand(arguments);

        case AddEventCommand.COMMAND_WORD:
            return prepareAddEventCommand(arguments);

        case AddToDoCommand.COMMAND_WORD:
            return prepareAddToDoCommand(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDeleteCommand(arguments);

        case DueCommand.COMMAND_WORD:
            return prepareDueCommand(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new InvalidCommand();
        }
    }

    private int parseArgsAsTaskNumber(String args) throws DukeException {
        Matcher matcher = TASK_NUMBER_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new DukeException(DukeExceptionType.NO_TASK_NUMBER);
        }
        return Integer.parseInt(matcher.group("taskNumber"));
    }
    
    public Deadline parseArgsAsDeadline(String args) throws DukeException {
        Matcher matcher = DEADLINE_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new DukeException(DukeExceptionType.INVALID_DEADLINE_FORMAT);
        }

        String deadlineDescription = matcher.group("description").trim();
        String dateString = matcher.group("deadline").trim();
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, DATE_TIME_FORMAT);

        return new Deadline(deadlineDescription, localDateTime);
    }

    public Event parseArgsAsEvent(String args) throws DukeException {
        Matcher matcher = EVENT_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new DukeException(DukeExceptionType.INVALID_EVENT_FORMAT);
        }

        String eventDescription = matcher.group("description").trim();
        String fromDateString  = matcher.group("fromDate").trim();
        String toDateString  = matcher.group("toDate").trim();

        LocalDateTime fromDate = LocalDateTime.parse(fromDateString, DATE_TIME_FORMAT);
        LocalDateTime toDate = LocalDateTime.parse(toDateString, DATE_TIME_FORMAT);

        if (fromDate.isAfter(toDate)) {
            throw new DukeException(DukeExceptionType.INVALID_EVENT_ARGUMENT);
        }

        return new Event(eventDescription, fromDate, toDate);
    }

    private Command prepareMarkCommand(String args) throws DukeException {
        int taskIndex = parseArgsAsTaskNumber(args);
        return new MarkCommand(taskIndex);
    }

    private Command prepareUnmarkCommand(String args) throws DukeException {
        int taskIndex = parseArgsAsTaskNumber(args);
        return new UnmarkCommand(taskIndex);
    }

    private Command prepareAddDeadlineCommand(String args) throws DukeException {
        Deadline deadline = parseArgsAsDeadline(args);
        return new AddDeadlineCommand(deadline);
    }

    private Command prepareAddEventCommand(String args) throws DukeException {
        Event event = parseArgsAsEvent(args);
        return new AddEventCommand(event);
    }

    private Command prepareAddToDoCommand(String args) {
        return new AddToDoCommand(new ToDo(args.trim()));
    }

    private Command prepareDeleteCommand(String args) throws DukeException {
        int taskIndex = parseArgsAsTaskNumber(args);
        return new DeleteCommand(taskIndex);
    }

    private Command prepareDueCommand(String args) {
        LocalDate dueDate = LocalDate.parse(args.trim(), DATE_FORMAT);
        return new DueCommand(dueDate);
    }
}
