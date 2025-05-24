package dev.izzulhaq.todo_list.controllers;

import dev.izzulhaq.todo_list.dto.request.TodoCategoryRequest;
import dev.izzulhaq.todo_list.dto.response.CommonResponse;
import dev.izzulhaq.todo_list.dto.response.TodoCategoryResponse;
import dev.izzulhaq.todo_list.services.TodoCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/categories")
public class TodoCategoryController {
    private final TodoCategoryService todoCategoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CommonResponse<TodoCategoryResponse>> create(@RequestBody TodoCategoryRequest request) {
        TodoCategoryResponse todoCategoryResponse = todoCategoryService.create(request);
        CommonResponse<TodoCategoryResponse> response = CommonResponse.<TodoCategoryResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Category created successfully.")
                .data(todoCategoryResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CommonResponse<TodoCategoryResponse>> getById(@PathVariable String id) {
        TodoCategoryResponse todoCategoryResponse = todoCategoryService.getById(id);
        CommonResponse<TodoCategoryResponse> response = CommonResponse.<TodoCategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved category data.")
                .data(todoCategoryResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<List<TodoCategoryResponse>>> getAll() {
        List<TodoCategoryResponse> todoCategoryResponse = todoCategoryService.getAll();
        CommonResponse<List<TodoCategoryResponse>> response = CommonResponse.<List<TodoCategoryResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved all category data.")
                .data(todoCategoryResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CommonResponse<List<TodoCategoryResponse>>> getAllByUser(@RequestParam String userId) {
        List<TodoCategoryResponse> todoCategoryResponse = todoCategoryService.getAllByUser(userId);
        CommonResponse<List<TodoCategoryResponse>> response = CommonResponse.<List<TodoCategoryResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved all category data from userId " + userId + ".")
                .data(todoCategoryResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CommonResponse<TodoCategoryResponse>> update(
            @PathVariable String id,
            @RequestBody TodoCategoryRequest request
    ) {
        TodoCategoryResponse todoCategoryResponse = todoCategoryService.update(id, request);
        CommonResponse<TodoCategoryResponse> response = CommonResponse.<TodoCategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully update category data.")
                .data(todoCategoryResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CommonResponse<?>> delete(@PathVariable String id) {
        todoCategoryService.delete(id);
        CommonResponse<?> response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully delete category data.")
                .build();
        return ResponseEntity.ok(response);
    }
}
