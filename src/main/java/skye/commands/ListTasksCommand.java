package skye.commands;

import skye.data.ListManager;
import skye.storage.StorageManager;
import skye.ui.UI;

/**
 * Represents the command to list all tasks.
 */
public class ListTasksCommand extends ListCommand {
    public static final String RESOURCE = "tasks";

    /**
     * Executes the list command by retrieving a list of tasks from the
     * TaskList  and displaying it on the UI.
     *
     * @param listManager ListManager
     * @param ui UI
     * @param storageManager StorageManager
     */
    @Override
    public String execute(ListManager listManager, UI ui, StorageManager storageManager) {
        return ui.showTasks(listManager.getTaskList().getTasks());
    }
}