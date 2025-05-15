package dev.izzulhaq.todo_list.services.impl;

import dev.izzulhaq.todo_list.constants.Constant;
import dev.izzulhaq.todo_list.constants.TodoStatus;
import dev.izzulhaq.todo_list.dto.request.SearchTodoRequest;
import dev.izzulhaq.todo_list.dto.request.TodoRequest;
import dev.izzulhaq.todo_list.dto.response.TodoResponse;
import dev.izzulhaq.todo_list.entities.Todo;
import dev.izzulhaq.todo_list.entities.UserAccount;
import dev.izzulhaq.todo_list.repositories.TodoRepository;
import dev.izzulhaq.todo_list.services.TodoService;
import dev.izzulhaq.todo_list.services.UserAccountService;
import dev.izzulhaq.todo_list.specifications.TodoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository;
    private final UserAccountService userAccountService;

    @Override
    public TodoResponse create(TodoRequest request) {
        UserAccount userAccount = userAccountService.getOne(request.getUserId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(request.getDate(), formatter);

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .todoDate(date)
                .status(TodoStatus.ONGOING)
                .userAccount(userAccount)
                .createdAt(LocalDateTime.now())
                .build();

        return mapToTodoResponse(repository.saveAndFlush(todo));
    }

    @Override
    public TodoResponse getById(String id) {
        return mapToTodoResponse(getOne(id));
    }

    @Override
    public Todo getOne(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.TODO_NOT_FOUND));
    }

    @Override
    public Page<TodoResponse> getAll(SearchTodoRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        Specification<Todo> specification = TodoSpecification.getSpecification(request);
        Page<Todo> todoPage = repository.findAll(specification, pageable);
        return todoPage.map(this::mapToTodoResponse);
    }

    @Override
    public TodoResponse update(String id, TodoRequest request) {
        Todo todo = getOne(id);

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setUpdatedAt(LocalDateTime.now());

        return mapToTodoResponse(repository.saveAndFlush(todo));
    }

    @Override
    public Todo updateStatus(String id, String newStatus) {
        Todo todo = getOne(id);

        TodoStatus status = TodoStatus.findByDescription(newStatus);

        todo.setStatus(status);
        todo.setUpdatedAt(LocalDateTime.now());

        return repository.saveAndFlush(todo);
    }

    @Override
    public TodoResponse reschedule(String id, String newDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(newDate, formatter);

        Todo todo = updateStatus(id, Constant.RESCHEDULED);

        Todo rescheduledTodo = Todo.builder()
                .title(todo.getTitle())
                .description(todo.getDescription())
                .todoDate(date)
                .status(TodoStatus.ONGOING)
                .userAccount(todo.getUserAccount())
                .createdAt(LocalDateTime.now())
                .build();

        return mapToTodoResponse(repository.saveAndFlush(rescheduledTodo));
    }

    @Override
    public TodoResponse cancel(String id) {
        return mapToTodoResponse(updateStatus(id, Constant.CANCELED));
    }

    @Override
    public TodoResponse finish(String id) {
        return mapToTodoResponse(updateStatus(id, Constant.FINISHED));
    }

    @Override
    public void delete(String id) {
        Todo todo = getOne(id);
        repository.delete(todo);
    }

    private TodoResponse mapToTodoResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .todoDate(todo.getTodoDate())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .status(todo.getStatus().name())
                .userId(todo.getUserAccount().getId())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
