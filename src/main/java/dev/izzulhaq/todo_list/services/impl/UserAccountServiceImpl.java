package dev.izzulhaq.todo_list.services.impl;

import dev.izzulhaq.todo_list.constants.Constant;
import dev.izzulhaq.todo_list.dto.request.SearchUserAccountRequest;
import dev.izzulhaq.todo_list.dto.request.UpdatePasswordRequest;
import dev.izzulhaq.todo_list.dto.response.UserAccountResponse;
import dev.izzulhaq.todo_list.entities.UserAccount;
import dev.izzulhaq.todo_list.repositories.UserAccountRepository;
import dev.izzulhaq.todo_list.services.UserAccountService;
import dev.izzulhaq.todo_list.specifications.UserAccountSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAccount create(UserAccount userAccount) {
        return repository.saveAndFlush(userAccount);
    }

    @Override
    public UserAccountResponse getById(String id) {
        return mapToUserAccountResponse(getOne(id));
    }

    @Override
    public UserAccount getOne(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.USER_NOT_FOUND));
    }

    @Override
    public Page<UserAccountResponse> getAll(SearchUserAccountRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        Specification<UserAccount> specification = UserAccountSpecification.getSpecification(request);

        Page<UserAccount> userAccountPage = repository.findAll(specification, pageable);
        return userAccountPage.map(this::mapToUserAccountResponse);
    }

    @Override
    public void updatePassword(String id, UpdatePasswordRequest request) {
        UserAccount userAccount = getOne(id);

        if (!checkPassword(userAccount, request.getCurrentPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constant.WRONG_PASSWORD);
        }

        userAccount.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userAccount.setUpdatedAt(LocalDateTime.now());
        repository.saveAndFlush(userAccount);
    }

    private Boolean checkPassword(UserAccount userAccount, String password) {
        return (passwordEncoder.matches(password, userAccount.getPassword()));
    }

    @Override
    public void delete(String id) {
        UserAccount userAccount = getOne(id);

        if (!userAccount.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.DELETE_USER_ERROR_MESSAGE);
        }

        userAccount.setIsActive(false);
        userAccount.setUpdatedAt(LocalDateTime.now());
        repository.saveAndFlush(userAccount);
    }

    private UserAccountResponse mapToUserAccountResponse(UserAccount userAccount) {
        return UserAccountResponse.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .role(userAccount.getRole().name())
                .createdAt(userAccount.getCreatedAt())
                .updatedAt(userAccount.getUpdatedAt())
                .isActive(userAccount.getIsActive())
                .build();
    }
}
