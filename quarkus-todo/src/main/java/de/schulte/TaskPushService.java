package de.schulte;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
@Loggable
public class TaskPushService {

    public void notifyAboutTask(Task task) {
        System.out.printf("notifyAboutTask %s%n", task);
    }

}
