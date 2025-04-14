package to_do_list;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String name;
    private int priority;
    private LocalDateTime creationTime;
    private Status status;
    private LocalDateTime executionTime;

    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.creationTime = LocalDateTime.now();
        this.status = Status.NOT_COMPLETED;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getExecutionTime() {
        return executionTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public void setExecutionTime(LocalDateTime executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public String toString() {
        String creation = creationTime != null
                ? creationTime.format(DATE_TIME_FORMATTER)
                : "None";

        String execution = executionTime != null
                ? executionTime.format(DATE_TIME_FORMATTER)
                : "None";
        return name + " | Priority: " + priority
                + " | Status: " + status
                + " | Creation time: " + creation
                + " | Execution time: " + execution;
    }
}

