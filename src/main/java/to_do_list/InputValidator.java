package to_do_list;

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
