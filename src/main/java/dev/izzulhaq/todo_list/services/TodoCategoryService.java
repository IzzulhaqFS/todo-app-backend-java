package dev.izzulhaq.todo_list.services;

import dev.izzulhaq.todo_list.dto.request.TodoCategoryRequest;
import dev.izzulhaq.todo_list.dto.response.TodoCategoryResponse;
import dev.izzulhaq.todo_list.entities.TodoCategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TodoCategoryService {
    TodoCategoryResponse create(TodoCategoryRequest request);
    TodoCategoryResponse getById(String id);
    TodoCategory getOne(String id);
    List<TodoCategoryResponse> getAll();
    TodoCategoryResponse update(String id, TodoCategoryRequest request);
    void delete(String id);
}
