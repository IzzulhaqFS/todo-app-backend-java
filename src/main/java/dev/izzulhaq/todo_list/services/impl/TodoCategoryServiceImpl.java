package dev.izzulhaq.todo_list.services.impl;

import dev.izzulhaq.todo_list.dto.request.TodoCategoryRequest;
import dev.izzulhaq.todo_list.dto.response.TodoCategoryResponse;
import dev.izzulhaq.todo_list.entities.TodoCategory;
import dev.izzulhaq.todo_list.entities.UserAccount;
import dev.izzulhaq.todo_list.repositories.TodoCategoryRepository;
import dev.izzulhaq.todo_list.services.TodoCategoryService;
import dev.izzulhaq.todo_list.services.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoCategoryServiceImpl implements TodoCategoryService {
    private final TodoCategoryRepository repository;
    private final UserAccountService userAccountService;

    @Override
    public TodoCategoryResponse create(TodoCategoryRequest request) {
        UserAccount userAccount = userAccountService.getOne(request.getUserId());

        TodoCategory category = TodoCategory.builder()
                .name(request.getName())
                .userAccount(userAccount)
                .build();

        return mapToTodoCategoryResponse(repository.saveAndFlush(category));
    }

    @Override
    public TodoCategoryResponse getById(String id) {
        return mapToTodoCategoryResponse(getOne(id));
    }

    @Override
    public TodoCategory getOne(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found."));
    }

    @Override
    public List<TodoCategoryResponse> getAll() {
        List<TodoCategory> todoCategoryList = repository.findAll();
        return todoCategoryList.stream().map(this::mapToTodoCategoryResponse).toList();
    }

    @Override
    public List<TodoCategoryResponse> getAllByUser(String userId) {
        List<TodoCategory> todoCategoryList = repository.findByUserAccountId(userId);
        return todoCategoryList.stream().map(this::mapToTodoCategoryResponse).toList();
    }

    @Override
    public TodoCategoryResponse update(String id, TodoCategoryRequest request) {
        TodoCategory category = getOne(id);

        category.setName(request.getName());

        return mapToTodoCategoryResponse(repository.saveAndFlush(category));
    }

    @Override
    public void delete(String id) {
        TodoCategory category = getOne(id);
        repository.delete(category);
    }

    private TodoCategoryResponse mapToTodoCategoryResponse(TodoCategory todoCategory) {
        return TodoCategoryResponse.builder()
                .id(todoCategory.getId())
                .name(todoCategory.getName())
                .userId(todoCategory.getUserAccount().getId())
                .build();
    }
}
