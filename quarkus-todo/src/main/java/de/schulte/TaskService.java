package de.schulte;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();

    public Task store(Task task) {
        this.tasks.put(task.id(), task);
        return task;
    }

    public List<Task> getAllTasks() {
        return this.tasks.values().stream().toList();
    }

    public Task getTask(long id) {
        return this.tasks.get(id);
    }

}

