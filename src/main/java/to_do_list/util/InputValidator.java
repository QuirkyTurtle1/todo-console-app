package to_do_list.util;

import to_do_list.model.Task;
import to_do_list.service.TaskManager;

import java.util.List;

public class InputValidator {
    public static boolean isIndexValid(int index, List<Task> taskList) {
        return index >= 0 && index < taskList.size();
    }
    public static boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty();
    }
    public static boolean isPriorityValid(int priority) {
        return priority >= 1 && priority <= 10;
    }


    public static boolean isTaskListEmpty(TaskManager manager) {
        return manager.getTaskList().isEmpty();
    }
}
