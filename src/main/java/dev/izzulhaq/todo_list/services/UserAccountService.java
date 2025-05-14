package dev.izzulhaq.todo_list.services;

import dev.izzulhaq.todo_list.dto.request.SearchUserAccountRequest;
import dev.izzulhaq.todo_list.dto.request.UpdatePasswordRequest;
import dev.izzulhaq.todo_list.dto.response.UserAccountResponse;
import dev.izzulhaq.todo_list.entities.UserAccount;
import org.springframework.data.domain.Page;

public interface UserAccountService {
    UserAccount create(UserAccount userAccount);
    UserAccountResponse getById(String id);
    UserAccount getOne(String id);
    Page<UserAccountResponse> getAll(SearchUserAccountRequest request);
    void updatePassword(String id, UpdatePasswordRequest request);
    void delete(String id);
}
