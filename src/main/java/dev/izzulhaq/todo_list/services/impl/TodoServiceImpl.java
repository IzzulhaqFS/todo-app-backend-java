package dev.izzulhaq.todo_list.services.impl;

import dev.izzulhaq.todo_list.constants.Constant;
import dev.izzulhaq.todo_list.constants.TodoPriority;
import dev.izzulhaq.todo_list.constants.TodoStatus;
import dev.izzulhaq.todo_list.dto.request.SearchTodoRequest;
import dev.izzulhaq.todo_list.dto.request.TodoRequest;
import dev.izzulhaq.todo_list.dto.response.SubTaskResponse;
import dev.izzulhaq.todo_list.dto.response.TodoResponse;
import dev.izzulhaq.todo_list.dto.response.UserAccountResponse;
import dev.izzulhaq.todo_list.entities.SubTask;
import dev.izzulhaq.todo_list.entities.Todo;
import dev.izzulhaq.todo_list.entities.TodoCategory;
import dev.izzulhaq.todo_list.entities.UserAccount;
import dev.izzulhaq.todo_list.repositories.TodoRepository;
import dev.izzulhaq.todo_list.services.SubTaskService;
import dev.izzulhaq.todo_list.services.TodoCategoryService;
import dev.izzulhaq.todo_list.services.TodoService;
import dev.izzulhaq.todo_list.services.UserAccountService;
import dev.izzulhaq.todo_list.specifications.TodoSpecification;
import dev.izzulhaq.todo_list.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository;
    private final UserAccountService userAccountService;
    private final TodoCategoryService todoCategoryService;
    private final SubTaskService subTaskService;

    @Override
    public TodoResponse create(TodoRequest request) {
        UserAccount userAccount = userAccountService.getOne(request.getUserId());
        TodoCategory category = todoCategoryService.getOne(request.getCategoryId());
        TodoPriority priority = TodoPriority.findByDescription(request.getPriority());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(request.getDeadline(), formatter);

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(date)
                .status(TodoStatus.ONGOING)
                .userAccount(userAccount)
                .category(category)
                .priority(priority)
                .createdAt(LocalDateTime.now())
                .build();

        Todo newTodo = repository.saveAndFlush(todo);

        if (request.getSubTasks() != null && !request.getSubTasks().isEmpty()) {
            List<SubTask> subTaskList = request.getSubTasks().stream().map(subTaskRequest -> SubTask.builder()
                    .name(subTaskRequest.getName())
                    .description(subTaskRequest.getDescription())
                    .isCompleted(false)
                    .todo(todo)
                    .build()
            ).toList();
            subTaskService.createBulk(subTaskList);
            newTodo.setSubTasks(subTaskList);
        }

        return MapperUtil.mapToTodoResponse(newTodo);
    }

    @Override
    public TodoResponse getById(String id) {
        Todo todo = getOne(id);
        List<SubTask> subTaskList = subTaskService.getAllByTodo(todo.getId());

        if (subTaskList != null && !subTaskList.isEmpty()) {
            todo.setSubTasks(subTaskList);
        }

        checkDeadline(todo);
        repository.saveAndFlush(todo);
        return MapperUtil.mapToTodoResponse(todo);
    }

    private void checkDeadline(Todo todo) {
        LocalDateTime now = LocalDateTime.now();
        if (todo.getDeadline().isBefore(now) && (todo.getStatus() == TodoStatus.ONGOING || todo.getStatus() == TodoStatus.NEAR_DEADLINE)) {
            updateStatus(todo, "overdue");
        } else if (todo.getDeadline().isBefore(now.plusDays(1)) && todo.getStatus() == TodoStatus.ONGOING) {
            updateStatus(todo, "near deadline");
        }
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
        todoPage.getContent().forEach(this::checkDeadline);
        repository.saveAllAndFlush(todoPage.getContent());
        return todoPage.map(MapperUtil::mapToTodoResponse);
    }

    @Override
    public TodoResponse update(String id, TodoRequest request) {
        Todo todo = getOne(id);
        TodoCategory category = todoCategoryService.getOne(request.getCategoryId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(request.getDeadline(), formatter);

        TodoPriority priority = TodoPriority.findByDescription(request.getPriority());

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCategory(category);
        todo.setDeadline(date);
        todo.setPriority(priority);
        todo.setUpdatedAt(LocalDateTime.now());

        return MapperUtil.mapToTodoResponse(repository.saveAndFlush(todo));
    }

    @Override
    public void complete(String id) {
        Todo todo = getOne(id);
        updateStatus(todo, "completed");
        repository.saveAndFlush(todo);
    }

    private void updateStatus(Todo todo, String newStatus) {
        TodoStatus status = TodoStatus.findByDescription(newStatus);

        todo.setStatus(status);
        todo.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public void delete(String id) {
        Todo todo = getOne(id);
        repository.delete(todo);
    }
}
