package dev.izzulhaq.todo_list.services.impl;

import dev.izzulhaq.todo_list.constants.Constant;
import dev.izzulhaq.todo_list.constants.RoleEnum;
import dev.izzulhaq.todo_list.dto.request.AuthRequest;
import dev.izzulhaq.todo_list.dto.response.AuthResponse;
import dev.izzulhaq.todo_list.entities.UserAccount;
import dev.izzulhaq.todo_list.security.JwtTokenProvider;
import dev.izzulhaq.todo_list.services.AuthService;
import dev.izzulhaq.todo_list.services.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountService userAccountService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse adminRegister(AuthRequest request) {
        try {
            RoleEnum role = RoleEnum.ROLE_ADMIN;

            UserAccount userAccount = createUserAccount(request, role);

            return AuthResponse.builder()
                    .username(userAccount.getUsername())
                    .role(userAccount.getRole().name())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, Constant.DUPLICATE_USER_ERROR_MESSAGE);
        }
    }

    @Override
    public AuthResponse userRegister(AuthRequest request) {
        try {
            RoleEnum role = RoleEnum.ROLE_USER;

            UserAccount userAccount = createUserAccount(request, role);

            return AuthResponse.builder()
                    .username(userAccount.getUsername())
                    .role(userAccount.getRole().name())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, Constant.DUPLICATE_USER_ERROR_MESSAGE);
        }
    }

    private UserAccount createUserAccount(AuthRequest request, RoleEnum role) {
        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();
        return userAccountService.create(userAccount);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = tokenProvider.generateToken(user.getUsername(), user.getAuthorities().toString());
        return AuthResponse.builder()
                .username(user.getUsername())
                .role(user.getAuthorities().toString())
                .token(token)
                .build();
    }
}
