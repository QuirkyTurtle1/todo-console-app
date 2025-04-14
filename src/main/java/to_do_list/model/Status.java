package to_do_list.model;

public enum Status {
    NOT_COMPLETED("Не выполнено"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Выполнено");

    private final String description;

    Status(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
