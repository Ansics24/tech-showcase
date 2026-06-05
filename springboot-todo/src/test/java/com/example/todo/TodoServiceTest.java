package com.example.todo;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoDto;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo sampleTodo;

    @BeforeEach
    void setUp() {
        sampleTodo = Todo.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .completed(false)
                .priority(Todo.Priority.MEDIUM)
                .build();
        // Simulate @PrePersist
        sampleTodo.setCreatedAt(LocalDateTime.now());
        sampleTodo.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should return all todos ordered by creation date")
    void getAllTodos_returnsAll() {
        when(todoRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(sampleTodo));
        List<TodoDto.Response> result = todoService.getAllTodos();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Task");
    }

    @Test
    @DisplayName("Should return todo by ID")
    void getTodoById_found() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        TodoDto.Response res = todoService.getTodoById(1L);
        assertThat(res.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should throw when todo not found by ID")
    void getTodoById_notFound() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> todoService.getTodoById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Should create a new todo")
    void createTodo_success() {
        TodoDto.Request request = TodoDto.Request.builder()
                .title("New Task")
                .description("Desc")
                .priority(Todo.Priority.HIGH)
                .build();
        when(todoRepository.save(any(Todo.class))).thenReturn(sampleTodo);
        TodoDto.Response res = todoService.createTodo(request);
        assertThat(res).isNotNull();
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("Should toggle completion status")
    void toggleComplete_flipsFalseToTrue() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        when(todoRepository.save(any(Todo.class))).thenAnswer(inv -> inv.getArgument(0));
        TodoDto.Response res = todoService.toggleComplete(1L);
        assertThat(res.isCompleted()).isTrue();
    }

    @Test
    @DisplayName("Should delete existing todo")
    void deleteTodo_success() {
        when(todoRepository.existsById(1L)).thenReturn(true);
        todoService.deleteTodo(1L);
        verify(todoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw when deleting non-existent todo")
    void deleteTodo_notFound() {
        when(todoRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> todoService.deleteTodo(99L))
                .isInstanceOf(RuntimeException.class);
    }
}
