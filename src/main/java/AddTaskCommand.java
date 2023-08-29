import java.io.IOException;

public class AddTaskCommand extends Command {
    private final Task task;

    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) throws IOException {
        taskList.addTask(task);
        storage.write(taskList.getTasks());
        ui.showAddedTask(task, taskList.getTasks());
    }
}
