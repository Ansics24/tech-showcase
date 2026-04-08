package de.schulte.service;

import de.schulte.Task;

public interface TaskPushService {
    void notifyAboutTask(Task task);
}
