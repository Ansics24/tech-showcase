package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoDto;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<TodoDto.Response> getAllTodos() {
        return todoRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TodoDto.Response getTodoById(Long id) {
        Todo todo = findTodoById(id);
        return toResponse(todo);
    }

    public TodoDto.Response createTodo(TodoDto.Request request) {
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.isCompleted())
                .priority(request.getPriority() != null ? request.getPriority() : Todo.Priority.MEDIUM)
                .build();
        return toResponse(todoRepository.save(todo));
    }

    public TodoDto.Response updateTodo(Long id, TodoDto.Request request) {
        Todo todo = findTodoById(id);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        if (request.getPriority() != null) {
            todo.setPriority(request.getPriority());
        }
        return toResponse(todoRepository.save(todo));
    }

    public TodoDto.Response toggleComplete(Long id) {
        Todo todo = findTodoById(id);
        todo.setCompleted(!todo.isCompleted());
        return toResponse(todoRepository.save(todo));
    }

    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TodoDto.Response> searchTodos(String query) {
        return todoRepository.searchTodos(query)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TodoDto.Response> getTodosByStatus(boolean completed) {
        return todoRepository.findByCompletedOrderByCreatedAtDesc(completed)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private Todo findTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    private TodoDto.Response toResponse(Todo todo) {
        return TodoDto.Response.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .priority(todo.getPriority())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
