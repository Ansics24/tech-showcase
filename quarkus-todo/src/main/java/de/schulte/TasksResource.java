package de.schulte;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/tasks")
public class TasksResource {

    private final Map<Long, Task> tasks = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getAllTasks() {
        return this.tasks.values().stream().toList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task getTask(@PathParam("id") long id) {
        return this.tasks.get(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Task store(Task task) {
        tasks.put(task.id(), task);
        return task;
    }

    @DELETE
    public void deleteTask(long id) {
        this.tasks.remove(id);
    }
}
