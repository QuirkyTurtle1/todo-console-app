package to_do_list;

public class TaskTest {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        manager.addTask(new Task("Купить продукты", 5));
        manager.addTask(new Task("Написать отчёт", 8));
        manager.addTask(new Task("Позвонить другу", 3));

        manager.printAllTasks();

    }
}
