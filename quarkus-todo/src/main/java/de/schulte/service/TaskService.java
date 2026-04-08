package de.schulte.service;

import de.schulte.Task;

import java.util.List;

public interface TaskService {

    Task store(Task task);

    List<Task> getAllTasks();

    Task getTask(long id);
}
