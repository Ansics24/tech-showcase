package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByCompletedOrderByCreatedAtDesc(boolean completed);

    List<Todo> findByPriorityOrderByCreatedAtDesc(Todo.Priority priority);

    List<Todo> findAllByOrderByCreatedAtDesc();

    @Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY t.createdAt DESC")
    List<Todo> searchTodos(String query);
}
