package de.schulte.service;

import de.schulte.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TaskServiceImpl implements TaskService {

    private final TaskPushService taskPushService;

    private final Map<Long, Task> tasks = new HashMap<>();

    @Inject
    public TaskServiceImpl(TaskPushService taskPushService) {
        this.taskPushService = taskPushService;
    }

    @Override
    public Task store(Task task) {
        System.out.printf("Task-Service %s%n", hashCode());
        this.taskPushService.notifyAboutTask(task);
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

    @Override
    public void deleteTask(long id) {
        this.tasks.remove(id);
    }

}

