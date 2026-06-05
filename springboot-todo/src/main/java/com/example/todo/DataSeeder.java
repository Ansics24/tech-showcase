package com.example.todo;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final TodoRepository todoRepository;

    @Override
    public void run(String... args) {
        todoRepository.save(Todo.builder()
                .title("Set up the Spring Boot project")
                .description("Initialize the project with Maven, configure dependencies and application properties.")
                .completed(true)
                .priority(Todo.Priority.HIGH)
                .build());

        todoRepository.save(Todo.builder()
                .title("Design the REST API endpoints")
                .description("Plan and implement CRUD endpoints: GET, POST, PUT, PATCH, DELETE for todos.")
                .completed(true)
                .priority(Todo.Priority.HIGH)
                .build());

        todoRepository.save(Todo.builder()
                .title("Build the frontend GUI")
                .description("Create a clean, responsive single-page interface for managing todos.")
                .completed(false)
                .priority(Todo.Priority.MEDIUM)
                .build());

        todoRepository.save(Todo.builder()
                .title("Write unit tests")
                .description("Add JUnit tests for the service and controller layers.")
                .completed(false)
                .priority(Todo.Priority.MEDIUM)
                .build());

        todoRepository.save(Todo.builder()
                .title("Deploy to production")
                .description("Package the application as a JAR and deploy to a cloud provider.")
                .completed(false)
                .priority(Todo.Priority.LOW)
                .build());
    }
}
