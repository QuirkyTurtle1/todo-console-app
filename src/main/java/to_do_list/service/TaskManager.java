package to_do_list.service;


import to_do_list.model.Status;
import to_do_list.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Task> filterByStatus(Status status) {
        return taskList.stream().filter(task -> task.getStatus() == status).collect(Collectors.toList());
    }

    public List<Task> filterByPriorityRange(int min, int max) {
        return taskList.stream().filter(task -> task.getPriority() >= min && task.getPriority() <= max).collect(Collectors.toList());
    }


    public List<Task> getTaskList() {
        return taskList;
    }

    public List<Task> searchByKeyword(String keyword) {
        return taskList.stream().filter(task -> task.getName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
    }
    public Map<Status, Long> countByStatus() {
        return taskList.stream().collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }
    public double averagePriority() {
        return taskList.stream().mapToInt(Task::getPriority).average().orElse(0.0);
    }
    public Map<Status, List<Task>> groupByStatus() {
        return taskList.stream().collect(Collectors.groupingBy(Task::getStatus));
    }
}
