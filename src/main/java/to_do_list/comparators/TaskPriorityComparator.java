package to_do_list.comparators;

import to_do_list.model.Task;

import java.util.Comparator;

public class TaskPriorityComparator implements Comparator<Task> {

    @Override
    public int compare(Task task1, Task task2) {
        return task2.getPriority()-task1.getPriority();
    }
}
