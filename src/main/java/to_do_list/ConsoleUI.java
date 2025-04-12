package to_do_list;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    public static boolean checkAndWarnIfListEmpty(TaskManager manager) {
        if (InputValidator.isTaskListEmpty(manager)) {
            System.out.println("Task list is empty, please add some tasks\n");
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager manager = new TaskManager();
        System.out.println("Welcome to the \"Task Management Application\"!\n");
        while (true) {
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
            int choice;

            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 9.\n");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> {
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
                case 2 -> {
                    if (checkAndWarnIfListEmpty(manager)) {
                        continue;
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
                case 3 -> {
                    if (checkAndWarnIfListEmpty(manager)) {
                        continue;
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
                case 4 -> {
                    if (checkAndWarnIfListEmpty(manager)) {
                        continue;
                    }
                    System.out.println("All tasks: ");
                    manager.printAllTasks();
                }
                case 7 -> {
                    if (checkAndWarnIfListEmpty(manager)) {
                        continue;
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
                                    System.out.println("\nTask updated:\n" + updatedTask + "\n");
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
                default -> System.out.println("Please enter a number from 1 to 9.\n");

            }

        }
    }
}
