package dev.izzulhaq.todo_list.services;

import dev.izzulhaq.todo_list.dto.request.SearchTodoRequest;
import dev.izzulhaq.todo_list.dto.request.TodoRequest;
import dev.izzulhaq.todo_list.dto.response.TodoResponse;
import dev.izzulhaq.todo_list.entities.Todo;
import org.springframework.data.domain.Page;

public interface TodoService {
    TodoResponse create(TodoRequest request);
    TodoResponse getById(String id);
    Todo getOne(String id);
    Page<TodoResponse> getAll(SearchTodoRequest request);
    TodoResponse update(String id, TodoRequest request);
    void delete(String id);
}
