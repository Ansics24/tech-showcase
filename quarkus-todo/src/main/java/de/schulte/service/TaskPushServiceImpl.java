package de.schulte.service;

import de.schulte.Task;
import de.schulte.interceptors.Loggable;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
@Loggable
public class TaskPushServiceImpl implements TaskPushService {

    @Override
    public void notifyAboutTask(Task task) {
        System.out.printf("notifyAboutTask %s%n", task);
    }

}
