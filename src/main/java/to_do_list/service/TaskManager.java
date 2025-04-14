package to_do_list.service;


import to_do_list.model.Status;
import to_do_list.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<>();
    }


    public void addTask(Task task) {
        taskList.add(task);
    }

    public void removeTask(int index) {
        taskList.remove(index);
    }


    public void updateStatus(int index, Status newStatus) {
        if (newStatus.equals(Status.COMPLETED)) {
            Task task = taskList.get(index);
            task.setStatus(Status.COMPLETED);
            task.setExecutionTime(LocalDateTime.now());
        }
        if (newStatus.equals(Status.IN_PROGRESS)) {
            taskList.get(index).setStatus(Status.IN_PROGRESS);
        }
        if (newStatus.equals(Status.NOT_COMPLETED)) {
            taskList.get(index).setStatus(Status.NOT_COMPLETED);
            taskList.get(index).setExecutionTime(null);
        }
    }

    public void printAllTasks() {
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }
    public List<Task> getTaskList() {
        return taskList;
    }

}
