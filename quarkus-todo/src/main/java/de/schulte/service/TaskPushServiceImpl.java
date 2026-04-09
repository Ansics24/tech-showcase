package de.schulte.service;

import de.schulte.Task;
import jakarta.enterprise.context.Dependent;

@Dependent
public class TaskPushServiceImpl implements TaskPushService {

    @Override
    public void notifyAboutTask(Task task) {
        System.out.printf("notifyAboutTask %s (Hashcode=%s)%n", task, hashCode());
    }

}
