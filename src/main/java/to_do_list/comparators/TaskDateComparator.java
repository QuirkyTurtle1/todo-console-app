package to_do_list.comparators;

import to_do_list.Task ;

import java.util.Comparator;

public class TaskDateComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        return task1.getCreationTime().compareTo(task2.getCreationTime());
    }
}
