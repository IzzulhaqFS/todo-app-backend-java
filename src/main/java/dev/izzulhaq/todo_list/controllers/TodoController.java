package dev.izzulhaq.todo_list.controllers;

import dev.izzulhaq.todo_list.dto.request.SearchTodoRequest;
import dev.izzulhaq.todo_list.dto.request.TodoRequest;
import dev.izzulhaq.todo_list.dto.response.CommonResponse;
import dev.izzulhaq.todo_list.dto.response.PagingResponse;
import dev.izzulhaq.todo_list.dto.response.TodoResponse;
import dev.izzulhaq.todo_list.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/todos")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<CommonResponse<TodoResponse>> create(@RequestBody TodoRequest request) {
        TodoResponse todoResponse = todoService.create(request);
        CommonResponse<TodoResponse> response = CommonResponse.<TodoResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Todo created successfully.")
                .data(todoResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TodoResponse>> getById(@PathVariable String id) {
        TodoResponse todoResponse = todoService.getById(id);
        CommonResponse<TodoResponse> response = CommonResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved todo data.")
                .data(todoResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TodoResponse>>> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "todoDate", required = false) String todoDate,
            @RequestParam(name = "userId", required = false) String userId
    ) {
        SearchTodoRequest request = SearchTodoRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .title(title)
                .status(status)
                .todoDate(todoDate)
                .userId(userId)
                .build();
        Page<TodoResponse> todoResponsePage = todoService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(todoResponsePage.getTotalPages())
                .totalElement(todoResponsePage.getTotalElements())
                .page(todoResponsePage.getPageable().getPageNumber())
                .size(todoResponsePage.getPageable().getPageSize())
                .hasNext(todoResponsePage.hasNext())
                .hasPrevious(todoResponsePage.hasPrevious())
                .build();
        CommonResponse<List<TodoResponse>> response = CommonResponse.<List<TodoResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved all todo data.")
                .data(todoResponsePage.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TodoResponse>> update(
            @PathVariable String id,
            @RequestBody TodoRequest request
    ) {
        TodoResponse todoResponse = todoService.update(id, request);
        CommonResponse<TodoResponse> response = CommonResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully update todo data.")
                .data(todoResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<CommonResponse<TodoResponse>> reschedule(
            @PathVariable String id,
            @RequestParam(name = "newDate") String newDate
    ) {
        TodoResponse todoResponse = todoService.reschedule(id, newDate);
        CommonResponse<TodoResponse> response = CommonResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully reschedule todo.")
                .data(todoResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<CommonResponse<TodoResponse>> cancel(
            @PathVariable String id
    ) {
        TodoResponse todoResponse = todoService.cancel(id);
        CommonResponse<TodoResponse> response = CommonResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully cancel todo.")
                .data(todoResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/finish")
    public ResponseEntity<CommonResponse<TodoResponse>> finish(
            @PathVariable String id
    ) {
        TodoResponse todoResponse = todoService.finish(id);
        CommonResponse<TodoResponse> response = CommonResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully finish todo.")
                .data(todoResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> delete(
            @PathVariable String id
    ) {
        todoService.delete(id);
        CommonResponse<?> response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully delete todo data.")
                .build();
        return ResponseEntity.ok(response);
    }
}
