package to_do_list.ui;


import to_do_list.util.InputValidator;
import to_do_list.model.Status;
import to_do_list.model.Task;
import to_do_list.service.TaskManager;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {
    public final Scanner sc = new Scanner(System.in);
    public final TaskManager manager = new TaskManager();

    public void run() {
        System.out.println("Welcome to the \"Task Management Application\"!\n");
        while (true) {
            printMenu();
            int choice = readMenuChoice();
            handleChoice(choice);
        }
    }

    private void printMenu() {
        System.out.println("1. Add a task");
        System.out.println("2. Delete a task");
        System.out.println("3. Edit a task");
        System.out.println("4. Show all tasks");
        System.out.println("5. Filter tasks");
        System.out.println("6. Find task by keyword");
        System.out.println("7. Change task status");
        System.out.println("8. Show statistics");
        System.out.println("9. Exit\n");
        System.out.print("Enter your choice: ");
    }

    private int readMenuChoice() {
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number from 1 to 9.\n");
            sc.nextLine();
            return -1;
        }
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addTaskUI();
            case 2 -> deleteTaskUI();
            case 3 -> editTaskUI();
            case 4 -> showAllTasksUI();
            case 5 -> filterTasksUI();
            case 6 -> findTaskUI();
            case 7 -> changeTaskStatusUI();
            case 8 -> showStatisticsUI();
            case 9 -> {
                System.out.println("Exiting the program. Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Please enter a number from 1 to 9.\n");
        }
    }

    private void showStatisticsUI() {
        if (checkAndWarnIfListEmpty(manager)) {
            return;
        }
        Map<Status, Long> counters = manager.countByStatus();
        double avgPriority = manager.averagePriority();
        Map<Status, List<Task>> map = manager.groupByStatus();
        System.out.println("\nTask statistics:");
        for (Status s : Status.values()) {
            long count = counters.getOrDefault(s, 0L);
            System.out.printf("- %-13s: %d%n", s.getDescription(), count);
        }
        System.out.printf("%nAverage priority: %.1f%n%n", avgPriority);
        for (Status s : Status.values()) {
            List<Task> list = map.getOrDefault(s, List.of());
            System.out.println(s.getDescription() + " (" + list.size() + "):");
            if (list.isEmpty()) {
                System.out.println("  -- no tasks --");
                return;
            }

            for (int i = 0; i < list.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + list.get(i));
            }
        }
        System.out.println();

    }

    private void findTaskUI() {
        if (checkAndWarnIfListEmpty(manager)) {
            return;
        }
        System.out.println("Enter keyword to search: ");
        String keyword = sc.nextLine().trim();
        if (keyword.isEmpty()) {
            System.out.println("Keyword cannot be empty\n");
            return;
        }
        List<Task> foundTask = manager.searchByKeyword(keyword);
        if (foundTask.isEmpty()) {
            System.out.println("No tasks found for \"" + keyword + "\".\n");
            return;
        } else

            System.out.println("\nTasks containing \"" + keyword + "\":");
        for (int i = 0; i < foundTask.size(); i++) {
            System.out.println((i + 1) + ". " + foundTask.get(i));
        }
        System.out.println();

    }


    private void addTaskUI() {
        System.out.print("Enter task name: ");
        String name = sc.nextLine();
        int priority;
        while (true) {
            System.out.print("Enter priority (1-10): ");
            if (sc.hasNextLine()) {
                priority = sc.nextInt();
                sc.nextLine();
                System.out.println();
                if (InputValidator.isPriorityValid(priority)) {
                    break;
                } else {
                    System.out.println("Priority must be between 1 and 10.");
                }
            } else {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
            }

        }
        manager.addTask(new Task(name, priority));
        System.out.println("Task added!\n");
    }

    private void deleteTaskUI() {
        if (checkAndWarnIfListEmpty(manager)) {
            return;
        }
        while (true) {
            System.out.print("Enter the number of the task to delete: ");
            int index = sc.nextInt() - 1;
            sc.nextLine();
            List<Task> taskList = manager.getTaskList();
            if (InputValidator.isIndexValid(index, taskList)) {
                Task removedTask = taskList.get(index);
                manager.removeTask(index);
                System.out.println("\nTask deleted:\n" + removedTask + "\n");
                break;
            } else {
                System.out.println("Invalid index. Try again.\n");
            }
        }
    }


    private int getTaskIndexFromUser() {
        try {
            int index = sc.nextInt() - 1;
            sc.nextLine();
            return index;
        } catch (InputMismatchException e) {
            System.out.println("Please enter valid number");
            sc.nextLine();
            return -1;
        }


    }

    private void editTaskUI() {
        if (checkAndWarnIfListEmpty(manager)) {
            return;
        }
        while (true) {
            System.out.println("Enter the number of the task to edit: ");
            int index = getTaskIndexFromUser();
            if (index == -1) {
                continue;
            }
            List<Task> taskList = manager.getTaskList();
            if (InputValidator.isIndexValid(index, taskList)) {
                Task taskToEdit = taskList.get(index);
                handleEditChoice(taskToEdit);
                break;
            } else {
                System.out.println("Invalid index. Try again.\n");
            }
        }
    }

    private void handleEditChoice(Task taskToEdit) {
        System.out.println("\nWhat do you want to edit?");
        System.out.println("1. Task name");
        System.out.println("2. Task priority");
        int editChoice = getEditChoice();
        switch (editChoice) {
            case 1 -> renameTask(taskToEdit);
            case 2 -> updatePriority(taskToEdit);
        }
    }

    private int getEditChoice() {
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int editChoice = sc.nextInt();
                sc.nextLine();
                if (editChoice == 1 || editChoice == 2) {
                    return editChoice;
                }
                System.out.println("Invalid choice. Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number (1 or 2).");
                sc.nextLine();
            }
        }
    }

    private void renameTask(Task taskToEdit) {
        while (true) {
            System.out.print("Enter new name: ");
            String newName = sc.nextLine();
            if (InputValidator.isNameValid(newName)) {
                taskToEdit.setName(newName);
                System.out.println("\nName updated:\n" + taskToEdit + "\n");
                break;
            } else {
                System.out.println("Invalid name. Try again.\n");
            }
        }
    }

    private void updatePriority(Task taskToEdit) {
        System.out.print("Enter new priority: ");
        int newPriority = readValidPriority();
        taskToEdit.setPriority(newPriority);
        System.out.println("\nPriority updated:\n" + taskToEdit + "\n");
    }

    private int readValidPriority() {
        while (true) {
            try {
                int input = sc.nextInt();
                sc.nextLine();
                if (InputValidator.isPriorityValid(input)) {
                    return input;
                } else {
                    System.out.println("Priority must be between 1 and 10. Try again: \n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number: ");
                sc.nextLine();
            }
        }
    }


    private void showAllTasksUI() {
        if (checkAndWarnIfListEmpty(manager)) {
            return;
        }
        System.out.println("\nShow all tasks with sorting:");
        System.out.println("1. By priority (descending)");
        System.out.println("2. By date (ascending)");
        int choice = getEditChoice();
        List<Task> sortedList = manager.getTaskList();
        switch (choice) {
            case 1 -> sortedList.sort(Comparator.comparingInt(Task::getPriority));
            case 2 -> sortedList.sort(Comparator.comparing(Task::getCreationTime));
            default -> {
                System.out.println("Invalid choice. Try again.");
                return;
            }
        }
        System.out.println("\n Sorted tasks:");
        for (int i = 0; i < sortedList.size(); i++) {
            System.out.println((i + 1) + ". " + sortedList.get(i));
        }
        System.out.println();
    }

    private void filterTasksUI() {
        if (checkAndWarnIfListEmpty(manager)) {
            return;
        }
        System.out.println("\nFilter tasks:");
        System.out.println("1. Filter by status (Completed, In progress, Not completed)");
        System.out.println("2. Filter by priority range (e.g. 3 to 7)");
        int choice = getEditChoice();
        switch (choice) {
            case 1 -> filterByStatus();
            case 2 -> filterByPriorityRange();
        }
    }

    private void filterByPriorityRange() {
        System.out.println("Enter minimum priority: ");
        int min = readValidPriority();
        System.out.print("Enter maximum priority: ");
        int max = readValidPriority();
        if (min > max) {
            System.out.println("Minimum priority cannot be greater than maximum priority.");
            return;
        }
        List<Task> filtered = manager.filterByPriorityRange(min, max);
        if (filtered.isEmpty()) {
            System.out.println("No tasks found in this priority range.");
            return;
        }
        System.out.println("\nTasks with priority from " + min + " to " + max + ":");
        for (int i = 0; i < filtered.size(); i++) {
            System.out.println((i + 1) + ". " + filtered.get(i));
        }
        System.out.println();


    }

    private void filterByStatus() {
        while (true) {
            System.out.print("Enter status (NOT_COMPLETED, IN_PROGRESS, COMPLETED): ");
            String input = sc.nextLine();
            try {
                Status status = Status.valueOf(input.trim().toUpperCase());
                List<Task> filtered = manager.filterByStatus(status);
                if (filtered.isEmpty()) {
                    System.out.println("No tasks with this status.");
                    return;
                }

                System.out.println("\nTasks with status: " + status.getDescription());
                for (int i = 0; i < filtered.size(); i++) {
                    System.out.println((i + 1) + ". " + filtered.get(i));
                }
                System.out.println();

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Try again.\n");
            }
        }

    }

    private void changeTaskStatusUI() {
        if (checkAndWarnIfListEmpty(manager)) {
            return;
        }
        while (true) {
            System.out.print("Enter the number of the task to update status: ");
            int index = sc.nextInt() - 1;
            sc.nextLine();
            List<Task> taskList = manager.getTaskList();
            if (InputValidator.isIndexValid(index, taskList)) {
                while (true) {
                    System.out.print("Enter new status (NOT_COMPLETED, IN_PROGRESS, COMPLETED): ");
                    String input = sc.nextLine();
                    try {
                        Status newStatus = Status.valueOf(input.trim().toUpperCase());
                        Task updatedTask = taskList.get(index);
                        manager.updateStatus(index, newStatus);
                        System.out.println("\nTask updated:");
                        System.out.println("Name: " + updatedTask.getName());
                        System.out.println("Priority: " + updatedTask.getPriority());
                        System.out.println("Status: " + newStatus.getDescription() + "\n");
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status. Try again.\n");
                    }
                }
                break;
            } else {
                System.out.println("Invalid index. Try again.\n");
            }
        }
    }

    public boolean checkAndWarnIfListEmpty(TaskManager manager) {
        if (InputValidator.isTaskListEmpty(manager)) {
            System.out.println("Task list is empty, please add some tasks\n");
            return true;
        }
        return false;
    }


}















