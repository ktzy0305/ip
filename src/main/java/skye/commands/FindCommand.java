package skye.commands;

import skye.data.TaskList;
import skye.data.task.Task;
import skye.storage.Storage;
import skye.ui.UI;

import java.util.List;

/**
 * Represents the command to find tasks containing the keyword specified by the user.
 */
public class FindCommand extends Command{

    public static final String COMMAND_WORD = "find";
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Execute the find tasks command and returns a list of tasks containing the keyword in
     * the description.
     *
     * @param taskList TaskList
     * @param ui UI
     * @param storage Storage
     */
    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) {
        List<Task> tasks = taskList.findTasksContaining(keyword);
        ui.showFoundTasks(tasks);
    }
}