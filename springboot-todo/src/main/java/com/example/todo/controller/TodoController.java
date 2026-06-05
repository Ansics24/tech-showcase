package com.example.todo.controller;

import com.example.todo.model.TodoDto;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    // GET all todos (optionally filter by status)
    @GetMapping
    public ResponseEntity<List<TodoDto.Response>> getAllTodos(
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String search) {
        List<TodoDto.Response> todos;
        if (search != null && !search.isBlank()) {
            todos = todoService.searchTodos(search);
        } else if (completed != null) {
            todos = todoService.getTodosByStatus(completed);
        } else {
            todos = todoService.getAllTodos();
        }
        return ResponseEntity.ok(todos);
    }

    // GET single todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto.Response> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    // POST create new todo
    @PostMapping
    public ResponseEntity<TodoDto.Response> createTodo(@Valid @RequestBody TodoDto.Request request) {
        TodoDto.Response created = todoService.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT update existing todo
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto.Response> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDto.Request request) {
        return ResponseEntity.ok(todoService.updateTodo(id, request));
    }

    // PATCH toggle completion status
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TodoDto.Response> toggleComplete(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.toggleComplete(id));
    }

    // DELETE a todo
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok(Map.of("message", "Todo deleted successfully"));
    }

    // Global exception handler for this controller
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
