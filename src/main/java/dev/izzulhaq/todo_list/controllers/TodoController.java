package dev.izzulhaq.todo_list.controllers;

import dev.izzulhaq.todo_list.constants.Constant;
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
@RequestMapping(path = Constant.TODO_API)
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<CommonResponse<TodoResponse>> create(@RequestBody TodoRequest request) {
        TodoResponse todoResponse = todoService.create(request);
        CommonResponse<TodoResponse> response = CommonResponse.<TodoResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message(Constant.CREATE_TODO_MESSAGE)
                .data(todoResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(Constant.USING_ID_ENDPOINT)
    public ResponseEntity<CommonResponse<TodoResponse>> getById(@PathVariable String id) {
        TodoResponse todoResponse = todoService.getById(id);
        CommonResponse<TodoResponse> response = CommonResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message(Constant.GET_TODO_BY_ID_MESSAGE)
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
            @RequestParam(name = "deadline", required = false) String deadline,
            @RequestParam(name = "userId", required = false) String userId,
            @RequestParam(name = "categoryId", required = false) String categoryId
    ) {
        SearchTodoRequest request = SearchTodoRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .title(title)
                .status(status)
                .deadline(deadline)
                .userId(userId)
                .categoryId(categoryId)
                .build();
        Page<TodoResponse> todoResponsePage = todoService.getAll(request);
        PagingResponse pagingResponse = getPagingResponse(todoResponsePage);
        CommonResponse<List<TodoResponse>> response = CommonResponse.<List<TodoResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(Constant.GET_ALL_TODO_MESSAGE)
                .data(todoResponsePage.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<CommonResponse<List<TodoResponse>>> getAllByUser(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "priority") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection,
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "deadline", required = false) String deadline,
            @RequestParam(name = "categoryId", required = false) String categoryId
    ) {
        SearchTodoRequest request = SearchTodoRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .title(title)
                .status(status)
                .deadline(deadline)
                .userId(userId)
                .categoryId(categoryId)
                .build();
        Page<TodoResponse> todoResponsePage = todoService.getAll(request);
        PagingResponse pagingResponse = getPagingResponse(todoResponsePage);
        CommonResponse<List<TodoResponse>> response = CommonResponse.<List<TodoResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved all todo from userId " + userId + ".")
                .data(todoResponsePage.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    private static PagingResponse getPagingResponse(Page<TodoResponse> todoResponsePage) {
        return PagingResponse.builder()
                .totalPage(todoResponsePage.getTotalPages())
                .totalElement(todoResponsePage.getTotalElements())
                .page(todoResponsePage.getPageable().getPageNumber())
                .size(todoResponsePage.getPageable().getPageSize())
                .hasNext(todoResponsePage.hasNext())
                .hasPrevious(todoResponsePage.hasPrevious())
                .build();
    }

    @PutMapping(Constant.USING_ID_ENDPOINT)
    public ResponseEntity<CommonResponse<TodoResponse>> update(
            @PathVariable String id,
            @RequestBody TodoRequest request
    ) {
        TodoResponse todoResponse = todoService.update(id, request);
        CommonResponse<TodoResponse> response = CommonResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message(Constant.UPDATE_TODO_MESSAGE)
                .data(todoResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(Constant.USING_ID_ENDPOINT)
    public ResponseEntity<CommonResponse<?>> delete(
            @PathVariable String id
    ) {
        todoService.delete(id);
        CommonResponse<?> response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(Constant.DELETE_TODO_MESSAGE)
                .build();
        return ResponseEntity.ok(response);
    }
}
