package de.schulte.service;

import de.schulte.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskServiceImpl implements TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();

    @Override
    public Task store(Task task) {
        this.tasks.put(task.id(), task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return this.tasks.values().stream().toList();
    }

    @Override
    public Task getTask(long id) {
        return this.tasks.get(id);
    }

}

