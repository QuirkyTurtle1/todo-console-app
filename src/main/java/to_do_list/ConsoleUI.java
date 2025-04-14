package to_do_list;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    public static final Scanner sc = new Scanner(System.in);
    public static final TaskManager manager = new TaskManager();

    public static void main(String[] args) {
        System.out.println("Welcome to the \"Task Management Application\"!\n");
        while (true) {
            printMenu();
            int choice = readMenuChoice();
            handleChoice(choice);
        }
    }
    private static void printMenu() {
        System.out.println("1. Add a task");
        System.out.println("2. Delete a task");
        System.out.println("3. Edit a task");
        System.out.println("4. Show all tasks");
        System.out.println("5. Filter tasks by status");
        System.out.println("6. Find task by keyword");
        System.out.println("7. Change task status");
        System.out.println("8. Show statistics");
        System.out.println("9. Exit\n");
        System.out.print("Enter your choice: ");
    }
    private static int readMenuChoice() {
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
    private static void handleChoice(int choice) {
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



    private static void addTaskUI() {
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

    private static void deleteTaskUI() {
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
    private static void editTaskUI() {
        if (checkAndWarnIfListEmpty(manager)) {
            return;
        }

        while (true) {
            try {
                System.out.print("Enter the number of the task to edit: ");
                int index = sc.nextInt() - 1;
                sc.nextLine();
                List<Task> taskList = manager.getTaskList();

                if (InputValidator.isIndexValid(index, taskList)) {
                    Task taskToEdit = taskList.get(index);
                    System.out.println("\nWhat do you want to edit?");
                    System.out.println("1. Task name");
                    System.out.println("2. Task priority");

                    int editChoice;
                    while (true) {
                        System.out.print("Enter your choice: ");
                        try {
                            editChoice = sc.nextInt();
                            sc.nextLine();
                            if (editChoice == 1 || editChoice == 2) break;
                            System.out.println("Invalid choice. Try again.");
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a number (1 or 2).");
                            sc.nextLine();
                        }
                    }

                    switch (editChoice) {
                        case 1 -> {
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
                        case 2 -> {
                            while (true) {
                                System.out.print("Enter new priority: ");
                                try {
                                    int newPriority = sc.nextInt();
                                    sc.nextLine();
                                    if (InputValidator.isPriorityValid(newPriority)) {
                                        taskToEdit.setPriority(newPriority);
                                        System.out.println("\nPriority updated:\n" + taskToEdit + "\n");
                                        break;
                                    } else {
                                        System.out.println("Invalid priority. Try again.\n");
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Please enter a valid number.");
                                    sc.nextLine();
                                }
                            }
                        }
                    }

                    break;
                } else {
                    System.out.println("Invalid index. Try again.\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
            }
        }
    }
    private static void showAllTasksUI() {
        if (checkAndWarnIfListEmpty(manager)) {
            return;
        }
        System.out.println("All tasks: ");
        manager.printAllTasks();
    }
    private static void changeTaskStatusUI() {
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

    public static boolean checkAndWarnIfListEmpty(TaskManager manager) {
        if (InputValidator.isTaskListEmpty(manager)) {
            System.out.println("Task list is empty, please add some tasks\n");
            return true;
        }
        return false;
    }

    }















