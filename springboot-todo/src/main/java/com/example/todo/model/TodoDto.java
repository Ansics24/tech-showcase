package com.example.todo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class TodoDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Title must not be blank")
        @Size(max = 255)
        private String title;

        @Size(max = 1000)
        private String description;

        private boolean completed;

        private Todo.Priority priority;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private boolean completed;
        private Todo.Priority priority;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
