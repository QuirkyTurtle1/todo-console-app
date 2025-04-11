package to_do_list;


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

    public void editTask(int index, String newName, int newPriority) {
        if (index >= taskList.size() || index < 0) {
            System.out.println("Invalid index. Try again.");
            return;
        }
        if (newName == null || newName.trim().isEmpty()) {
            System.out.println("Task name cannot be empty");
            return;
        }
        if (newPriority < 1 || newPriority > 10) {
            System.out.println("Priority must be between 1 and 10.");
            return;
        }
        Task editingTask = taskList.get(index);
        editingTask.setName(newName);
        editingTask.setPriority(newPriority);
    }

    public void updateStatus(int index, Status newStatus) {
        if (index >= taskList.size() || index < 0) {
            System.out.println("Invalid index. Try again.");
            return;
        }
        if (!newStatus.equals(Status.COMPLETED) &&
                !newStatus.equals(Status.IN_PROGRESS) &&
                !newStatus.equals(Status.NOT_COMPLETED)) {
            System.out.println("Status is not correct");
            return;
        }
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
        if (taskList.isEmpty()) {
            System.out.println("Task list is empty");
            return;
        }
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));

        }
    }
    public List<Task> getTaskList() {
        return taskList;
    }

}
