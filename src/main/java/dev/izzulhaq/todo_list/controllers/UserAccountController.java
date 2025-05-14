package dev.izzulhaq.todo_list.controllers;

import dev.izzulhaq.todo_list.dto.request.SearchUserAccountRequest;
import dev.izzulhaq.todo_list.dto.request.UpdatePasswordRequest;
import dev.izzulhaq.todo_list.dto.response.CommonResponse;
import dev.izzulhaq.todo_list.dto.response.PagingResponse;
import dev.izzulhaq.todo_list.dto.response.UserAccountResponse;
import dev.izzulhaq.todo_list.services.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserAccountResponse>> getById(@PathVariable String id) {
        UserAccountResponse userAccountResponse = userAccountService.getById(id);
        CommonResponse<UserAccountResponse> response = CommonResponse.<UserAccountResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved user.")
                .data(userAccountResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<UserAccountResponse>>> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "isActive", required = false) Boolean isActive,
            @RequestParam(name = "createdDateStart", required = false) String createdDateStart,
            @RequestParam(name = "createdDateEnd", required = false) String createdDateEnd,
            @RequestParam(name = "updatedDateStart", required = false) String updatedDateStart,
            @RequestParam(name = "updatedDateEnd", required = false) String updatedDateEnd
    ) {
        SearchUserAccountRequest request = SearchUserAccountRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .username(username)
                .role(role)
                .isActive(isActive)
                .createdDateStart(createdDateStart)
                .createdDateEnd(createdDateEnd)
                .updatedDateStart(updatedDateStart)
                .updatedDateEnd(updatedDateEnd)
                .build();
        Page<UserAccountResponse> userAccountResponsePage = userAccountService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(userAccountResponsePage.getTotalPages())
                .totalElement(userAccountResponsePage.getTotalElements())
                .page(userAccountResponsePage.getPageable().getPageNumber())
                .size(userAccountResponsePage.getPageable().getPageSize())
                .hasNext(userAccountResponsePage.hasNext())
                .hasPrevious(userAccountResponsePage.hasPrevious())
                .build();
        CommonResponse<List<UserAccountResponse>> response = CommonResponse.<List<UserAccountResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved all user.")
                .data(userAccountResponsePage.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> updatePassword(
            @PathVariable String id,
            @RequestBody UpdatePasswordRequest request
    ) {
        userAccountService.updatePassword(id, request);
        CommonResponse<?> response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully update user password.")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> delete(@PathVariable String id) {
        userAccountService.delete(id);
        CommonResponse<?> response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully delete user.")
                .build();
        return ResponseEntity.ok(response);
    }
}
