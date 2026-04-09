package de.schulte;

import de.schulte.service.TaskService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/tasks")
public class TasksResource {

    private final TaskService taskService;

    @Inject
    public TasksResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getAllTasks() {
        return this.taskService.getAllTasks();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task getTask(@PathParam("id") long id) {
        return this.taskService.getTask(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Task store(Task task) {
        return this.taskService.store(task);
    }

    @DELETE
    @Path("/{id}")
    public void deleteTask(@PathParam("id") Long id) {
        this.taskService.deleteTask(id);
    }
}
