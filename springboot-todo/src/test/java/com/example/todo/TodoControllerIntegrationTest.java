package com.example.todo;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/todos returns HTTP 200 with list")
    void getAll_returns200() throws Exception {
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /api/todos creates a new todo")
    void createTodo_returns201() throws Exception {
        TodoDto.Request req = TodoDto.Request.builder()
                .title("Integration Test Task")
                .description("Created via integration test")
                .priority(Todo.Priority.HIGH)
                .build();

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Integration Test Task"))
                .andExpect(jsonPath("$.priority").value("HIGH"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    @DisplayName("PUT /api/todos/{id} updates existing todo")
    void updateTodo_returns200() throws Exception {
        // First create one
        TodoDto.Request create = TodoDto.Request.builder()
                .title("Original Title")
                .priority(Todo.Priority.LOW)
                .build();
        MvcResult res = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(create)))
                .andReturn();
        Long id = objectMapper.readTree(res.getResponse().getContentAsString()).get("id").asLong();

        // Now update
        TodoDto.Request update = TodoDto.Request.builder()
                .title("Updated Title")
                .priority(Todo.Priority.HIGH)
                .completed(true)
                .build();
        mockMvc.perform(put("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    @DisplayName("PATCH /api/todos/{id}/toggle toggles completion")
    void toggle_flipsStatus() throws Exception {
        TodoDto.Request create = TodoDto.Request.builder()
                .title("Toggle Me")
                .priority(Todo.Priority.MEDIUM)
                .build();
        MvcResult res = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(create)))
                .andReturn();
        Long id = objectMapper.readTree(res.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(patch("/api/todos/" + id + "/toggle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    @DisplayName("DELETE /api/todos/{id} removes todo")
    void deleteTodo_returns200() throws Exception {
        TodoDto.Request create = TodoDto.Request.builder()
                .title("Delete Me")
                .priority(Todo.Priority.LOW)
                .build();
        MvcResult res = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(create)))
                .andReturn();
        Long id = objectMapper.readTree(res.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/api/todos/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/todos/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/todos with blank title returns 400")
    void createTodo_blankTitle_returns400() throws Exception {
        TodoDto.Request bad = TodoDto.Request.builder().title("  ").build();
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bad)))
                .andExpect(status().isBadRequest());
    }
}
